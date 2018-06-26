package com.echain.solidity;

import com.echain.conf.ParamsProperties;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfig {

    @Autowired
    ParamsProperties paramsProperties;

    @Bean(name = "web3j")
    public Web3j createWeb3j() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
        HttpService httpService = new HttpService(
                paramsProperties.getEthereumRpcIp(), client, false);
        Web3j web3j = Web3j.build(httpService);
//        web3j = Web3j.build(new HttpService(EchainConstant.ETHEREUM_RPC_IP));

        return web3j;
    }

    @Bean(name = "admin")
    public Admin createAdmin() {
        Admin admin = Admin.build(new HttpService(paramsProperties.getEthereumRpcIp()));
        return admin;
    }
}
