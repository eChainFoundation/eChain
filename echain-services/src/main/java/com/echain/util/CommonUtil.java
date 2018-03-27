package com.echain.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

import org.slf4j.Logger;

public class CommonUtil {
	public static final Charset gbkCharset = Charset.forName("GBK"), utf8Charset = Charset.forName("UTF-8"), iso8859Charset = Charset.forName("ISO8859-1");
	
	private static final String validUrlSpecialChars = "-_.!~*'();/?:@&=+$,"; // uric reserved and unreserved RFC2396
	public static String escapeUrl(String htmlUrl, Charset charset) {
		htmlUrl = htmlUrl.replaceAll("[\r\n]", ""); // There are such links in the advertising scripts in http://www.hjldy.com/oumeiju/f7a729b37c5f35c8/, and we don't want to escape them.
		if (! htmlUrl.startsWith("http")) return htmlUrl; // We don't want to escape e.g. bdhd links (escaped bdhd links should be un-escaped, but we don't know how...)
		// we have urls like http://static.bshare.cn/b/button.js#uuid=d9e12abe-833b-47d0-a217-93d8a01cd448&style=2&textcolor=#000&bgcolor=none&bp=renren%2Csinaminiblog%2Cqzone%2Cqqmb%2Cqqxiaoyou&ssc=false&text=%E5%88%86%E4%BA%AB%E5%88%B0, where the first # is the separator
		// The fragment part itself is mostly immaterial; the important thing is to make the URL accepted by the URL class, and also to determine the correct fragment part (so that normalizeVideoUrl can remove it if necessary), which necessitates proper escaping.
		int hashPos = htmlUrl.indexOf('#');
		if (hashPos == -1) return escapeUrlPart(htmlUrl, charset);
		else return escapeUrlPart(htmlUrl.substring(0, hashPos), charset) + "#" + escapeUrlPart(htmlUrl.substring(hashPos+1), charset);
	}
	
	public static String escapeUrlPart(String part, Charset charset) { // the part can be the value of a query variable, etc.
		StringBuilder buf = new StringBuilder();
		int rawLen = part.length(), pos = 0;
		while (pos < rawLen) {
			char rawCh = part.charAt(pos);
			if ((rawCh >= 'A' && rawCh <= 'Z') || (rawCh >= 'a' && rawCh <= 'z') || (rawCh >= '0' && rawCh <= '9')
				|| validUrlSpecialChars.indexOf(rawCh) != -1) {
				buf.append(rawCh); ++pos;
			} else if (rawCh == '%' && pos+2 < rawLen && validHexChars.indexOf(part.charAt(pos+1)) != -1 && validHexChars.indexOf(part.charAt(pos+2)) != -1) { // valid %EA sequences
				buf.append(part, pos, pos+3);  pos += 3;
			} else if (charset != null) { // escape the character.  Encoding character-by-character is rather inefficient, but hopefully we don't have too many such characters...
				ByteBuffer encBuf = charset.encode(part.substring(pos, pos+1));
				while (encBuf.hasRemaining()) {
					byte val = encBuf.get();
					buf.append('%');
					buf.append(CommonUtil.hexCharsUpper.charAt((val >>> 4) & 0x0f)); // without the &, the sign bit will be set (byte is a signed type...) 
					buf.append(CommonUtil.hexCharsUpper.charAt(val & 0x0f));
				}
				++pos;
			} else { // encode non-ASCII characters with unicode
				int val = (int) part.charAt(pos);
				if (val < 128) buf.append(String.format("%%%02X", val));
				else buf.append(String.format("%%u%04x", val));
				++pos;
			}
		}
		return buf.toString();
	}
	private static final String validHexChars = "0123456789ABCDEFabcdef";
	public static final String hexChars = "0123456789abcdef";
	public static final String hexCharsUpper = "0123456789ABCDEF";
	public static String toHexString(byte[] bytes) { // same as MySQL's HEX function
		int strLen = bytes.length * 2;
		StringBuilder buf = new StringBuilder(strLen);
		for (int i = 0; i < bytes.length; ++i) {
			byte val = bytes[i];
			buf.append(hexChars.charAt((val >>> 4) & 0x0f)); // without the &, the sign bit will be set (byte is a signed type...) 
			buf.append(hexChars.charAt(val & 0x0f));
		}
		return buf.toString();
	}
	
	public static String tryExtractMiddleString(String str, String prefix, String suffix) {
		str = str.trim();
		int prefixLen = prefix.length(), suffixLen = suffix.length(), rawLen = str.length();
		if (rawLen >= prefixLen + suffixLen && str.startsWith(prefix) && str.endsWith(suffix)) return str.substring(prefixLen, rawLen - suffixLen);
		else return null;
	}
	
	public static String urlUnescape(String rawStr, Charset charset) {
		try {
			final DecodeBuffer buf = new DecodeBuffer(charset);
			final int rawLen = rawStr.length();
			for (int i = 0; i < rawLen;) {
				char rawCh = rawStr.charAt(i++);
				if (rawCh == '%' && i+2 <= rawLen && isHexDigit(rawStr.charAt(i)) && isHexDigit(rawStr.charAt(i+1))) {
					buf.addByte((byte) Integer.parseInt(rawStr.substring(i, i+2), 16));  i += 2;
				} else if (rawCh == '+') buf.addChar(' ');
				else buf.addChar(rawCh);
			}
			buf.finalFlush();
			return buf.toString();
		} catch (CharacterCodingException ex) { throw new RuntimeException(ex); }
	}
	
	public static boolean isHexDigit(int ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
	}
	
	private static class DecodeBuffer {
		private ByteBuffer byteBuf = ByteBuffer.allocate(8); // 8 should be larger than the length of the longest character...
		private CharsetDecoder dec;
		private CharBuffer charBuf = CharBuffer.allocate(16);
		private StringBuilder buf = new StringBuilder();
		private boolean isPermissive = false;
		public void setPermissive(boolean isPermissive) { this.isPermissive = isPermissive; } // the string can occasionally contain bad characters

		public DecodeBuffer(Charset charset) {
			this.dec = charset.newDecoder();
			dec.onMalformedInput(CodingErrorAction.REPORT);  dec.onUnmappableCharacter(CodingErrorAction.REPORT);
		}
		public void addChar(char c) throws CharacterCodingException {
			flush(true); buf.append(c);
		}
		public void addByte(byte b) throws CharacterCodingException {
			byteBuf.put(b);  flush(false);
		}
		public void addBytes(byte[] b) throws CharacterCodingException {
			int i = 0, n = b.length;
			while (i < n) {
				final int count = Math.min(n-i, byteBuf.remaining());  if (count == 0) throw new RuntimeException("Lack of progress in DecodeBuffer#addBytes()");
				byteBuf.put(b, i, count);  i += count;
				flush(false);
			}
		}
		// Called when a byte sequence has ended
		private void flush(boolean endOfBytes) throws CharacterCodingException {
			byteBuf.flip(); // existing bytes are now available for reading
			try {
				while (true) {
					CoderResult res = dec.decode(byteBuf, charBuf, endOfBytes); // in practice even incomplete byte sequences are consumed
					if (res.isError()) {
						if (!isPermissive) res.throwException();
						else {
//							logger.warn("Decode error: " + res.toString());  byteBuf.clear();
						} // this can happen in the WebAnalyzer for e.g. http://www.7tvb.com/play/42508-0-121.html due to the occasional bad characters
					}
					//byteBuf.compact(); // move undecoded bytes to the beginning of the buffer (the buffer is not circular...)
					charBuf.flip(); buf.append(charBuf); charBuf.clear();
					if (res != CoderResult.OVERFLOW) break;
				}
			} finally {
				if (endOfBytes) { byteBuf.clear(); dec.reset(); charBuf.clear(); }
				else byteBuf.compact(); // move remaining bytes back to the beginning of the buffer
			}
		}
		// Since toString() cannot throw checked exceptions, this should be called before toString() if exceptions are to be preserved
		public void finalFlush() throws CharacterCodingException {
			flush(true);
		}
		public String toString() {
			try { flush(true); } catch (CharacterCodingException exc) { throw new RuntimeException(exc); }
			return buf.toString();
		}
	}
}
