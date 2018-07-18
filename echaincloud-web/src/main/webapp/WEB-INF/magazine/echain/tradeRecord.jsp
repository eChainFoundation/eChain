<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/swiper.css" rel="stylesheet" type="text/css">
    <link href="/static/font/iconfont.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/hotcss.js"></script>
    <title>交易记录</title>
</head>
<body style="background: #fff">
<header class="tradeHead">
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui color-white f-icon-s2" onclick="javascript:history.go(-1)"></i>
        <h2 class="t-box-flex1 t-center color-white f-s4">交易记录</h2>
    </div>
    <div class="SucBox">
        <img src="/static/img/tradeSuc.png" alt="">
    </div>
</header>
<section>
    <article>
        <div class="listBox">
            <p><span>${record.points}</span>${record.type}</p>
        </div>
        <div class="listConBox">
            <div class="picBox">
                <img src="/static/img/tradeSuc.png" alt="">
            </div>
            <div class="conBox">
                <p>
                    <span>发款方</span>
                </p>
                <p>${record.from}</p>
            </div>
        </div>
        <div class="listConBox">
            <div class="picBox">
                <img src="/static/img/tradeSuc.png" alt="">
            </div>
            <div class="conBox">
                <p>
                    <span>收款方</span>
                </p>
                <p>${record.to}</p>
                <p style="color: #b4b0bf;">手续费</p>
                <p style="color: #b4b0bf;">0 ECP</p>
            </div>
        </div>
        <div class="listConBox">
            <div class="picBox">
            </div>
            <div class="conBox">
                <p>
                    <span>交易号</span>
                </p>
                <p>${record.id}</p>
            </div>
        </div>
        <div class="listConBox">
            <div class="picBox">
            </div>
            <div class="conBox">
                <p>
                    <span>交易时间</span>
                </p>
                <p>${record.createTime}</p>
            </div>
        </div>
    </article>
</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
</body>
</html>