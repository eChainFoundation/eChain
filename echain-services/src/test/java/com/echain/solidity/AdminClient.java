package com.echain.solidity;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

public class AdminClient {

    private static String ip = "http://101.132.124.123:8078/";

    private AdminClient(){}

    private static class ClientHolder{
        private static final Admin admin = Admin.build(new HttpService(ip));
    }

    public static final Admin getAdmin(){
        return ClientHolder.admin;
    }
}