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
<html style="height: 100%;">
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
    <title>充值码</title>
</head>
<body class="rCBg">
<header class="bg-white">
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui f-icon-s2" onclick="javascript:history.go(-1)"></i>
        <h2 class="t-box-flex1 t-center f-s4">充值码</h2>
    </div>
</header>
<section>
    <article>
        <div class="headPicBox">
            <span>
                <img src="/static/img/headPic.png" alt="">
            </span>
            <p class="color-white">${phone}</p>
        </div>
    </article>
    <article class="showContent">
        <div class="showTxtBox">
            <p class="gatheringAddress" id="copyText">${wallet}</p>
        </div>
        <div class="qrCodeBox">
            <img src="${qImg}" alt="">
        </div>
        <p class="copyBox">
        <p class="copyBox">
            <textarea class="copyInput" style="opacity: 0;position: absolute;top: 1000px;"></textarea>
            <input type="button" id="copyBtn" data-clipboard-action="copy" data-clipboard-target="#copyText" value="复制收款地址" class="copyAddress">
        </p>
        </p>
    </article>
</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/clipboard.min.js"></script>
<script>
    var clipboard = new Clipboard('#copyBtn');
    clipboard.on('success', function(e) {
        alert( $("#copyText").text() + "\n复制成功！")
    });
    clipboard.on('error', function(e) {
        console.log(e);
    });

</script>
</body>
</html>