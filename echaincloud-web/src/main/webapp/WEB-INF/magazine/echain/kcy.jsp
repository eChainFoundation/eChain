<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%
/* String path = "http://echain.echain.one";
String basePath = "http://echain.echain.one"; */

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<link href="css/swiper.css" rel="stylesheet" type="text/css">
	<link href="font/iconfont.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/hotcss.js"></script>
	<title>Official Website</title>
</head>
<body>
<header class="withdraw-header">
	<div class="t-box header-top h-auto">
		<i class="iconfont icon-fanhui icon-fanhui-js f-icon-s2 color-white"></i>
		<h2 class="t-box-flex1 color-white f-s4 mar-l">Official Website</h2>
	</div>
</header>
<section>
	<div class="orderU-head">
		<div class="auto t-box">
			<div class="t-box-flex1">Please  upload your passport picture</div>
		</div>
	</div>
	<article class="auto">
		<div class="upload-photo">
			<img src="img/uploadImg.png" width="100%" />
			<input type="file" style="opacity: 0" />
		</div>
	
		<button type="button" class="btn eMall-btn-reg bg-public-con color-white">Submit</button>
		
	</article>

	<article class="kcy-tips auto">
		<p>People attend the public presale must satisfy the following conditions:</p>
		<p>1. Not Chinese or American.</p>
		<p>2. At most 5 ETH for each person.</p>
		<p>3. Know our products and able to assume the risks.</p>
	</article>
	
</section>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>

</body>
</html>