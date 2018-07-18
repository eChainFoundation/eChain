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
    <title>ECP</title>
</head>
<body class="bg-white">
<header class="re-header">
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui color-white f-icon-s2" onclick="javascript:history.go(-1)"></i>
        <h2 class="t-box-flex1 t-center color-white f-s4">${wallet.type}</h2>
    </div>

    <div class="headPicBox re-picBox">
            <span>
                <img src="/static/img/headPic.png" alt="">
            </span>
        <p class="color-white">${phone}</p>
        <p class="color-white re-font01">${wallet.value}</p>
        <%--<p class="color-white re-font02"><span>${wallet.rmb}</span>元</p>--%>
    </div>
</header>
<section>
    <article class="recordBox">
        <p class="recordBoxP">最近交易记录</p>
        <ul>
            <c:forEach items="${recordList}" var="item">
                <li class="re-con-box" style="cursor: pointer;" onclick="showDetail('${item.id}')">
                    <div class="picBox">
                        <img class="re-pic" src="/static/img/tradeSuc.png" alt="">
                    </div>
                    <div class="re-con01">
                        <p>${item.to}</p>
                        <p style="color: #b4b0bf;">${item.createTime}</p>
                    </div>
                    <div class="re-con02"><span>${item.points}</span> ${wallet.type}</div>
                </li>
            </c:forEach>
        </ul>
    </article>
    <article>
        <ul class="re-operate">
            <li class="re-operate-li01" onclick="recharge('${wallet.type}')" style="cursor: pointer;">
                <span>充值</span>
            </li>
            <li onclick="withdraw('${wallet.type}')" style="cursor: pointer;">
                <span>提现</span>
            </li>
        </ul>
    </article>
</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
<script>
    var userId = getQueryString("userId");

    function withdraw(type){
        window.location.href="<%=basePath%>/wallet/withdraw?userId=" + userId + "&type=" + type;
    }

    function recharge(type) {
        window.location.href="<%=basePath%>/wallet/img?userId=" + userId + "&type=" + type;
    }

    function showDetail(id){
        window.location.href="<%=basePath%>/wallet/recordDetail?id=" + id;
    }
</script>
</body>
</html>