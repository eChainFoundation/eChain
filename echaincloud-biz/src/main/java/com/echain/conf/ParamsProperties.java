package com.echain.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "params")
public class ParamsProperties {

    private String uploadImgPath;

    private String ethereumRpcIp;

    private String walletFile;

    private String walletPassword;

    private String epointsAddress;

    private String saveDataAddress;

    private int decimals;

    private String tickerSymbol;

    private String okexUrl;

    private String rateUrl;

    private String etherscanUrl;

    public String getUploadImgPath() {
        return uploadImgPath;
    }

    public void setUploadImgPath(String uploadImgPath) {
        this.uploadImgPath = uploadImgPath;
    }

    public String getEthereumRpcIp() {
        return ethereumRpcIp;
    }

    public void setEthereumRpcIp(String ethereumRpcIp) {
        this.ethereumRpcIp = ethereumRpcIp;
    }

    public String getWalletFile() {
        return walletFile;
    }

    public void setWalletFile(String walletFile) {
        this.walletFile = walletFile;
    }

    public String getWalletPassword() {
        return walletPassword;
    }

    public void setWalletPassword(String walletPassword) {
        this.walletPassword = walletPassword;
    }

    public String getEpointsAddress() {
        return epointsAddress;
    }

    public void setEpointsAddress(String epointsAddress) {
        this.epointsAddress = epointsAddress;
    }

    public String getSaveDataAddress() {
        return saveDataAddress;
    }

    public void setSaveDataAddress(String saveDataAddress) {
        this.saveDataAddress = saveDataAddress;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getOkexUrl() {
        return okexUrl;
    }

    public void setOkexUrl(String okexUrl) {
        this.okexUrl = okexUrl;
    }

    public String getRateUrl() {
        return rateUrl;
    }

    public void setRateUrl(String rateUrl) {
        this.rateUrl = rateUrl;
    }

    public String getEtherscanUrl() {
        return etherscanUrl;
    }

    public void setEtherscanUrl(String etherscanUrl) {
        this.etherscanUrl = etherscanUrl;
    }
}
