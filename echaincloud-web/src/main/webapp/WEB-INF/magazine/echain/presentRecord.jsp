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
    <title>提现记录</title>
</head>
<body class="bg-white">
<header>
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui f-icon-s2" onclick="javascript:history.go(-1)"></i>
        <h2 class="t-box-flex1 t-center f-s4">提现记录</h2>
    </div>
</header>
<section class="mt-section">
    <article>
        <ul>
            <c:forEach items="${list}" var="item">
                <li>
                    <div class="pLCon">
                        <p class="Conp01">
                            <span>编号：</span>
                            <span>${item.to}</span>
                        </p>
                        <p class="ConS02">${item.createTime}</p>
                    </div>
                    <div class="pRCon">
                        <p>
                            <span class="ConS02">${item.status}</span>
                        </p>
                        <p class="ConP02">${item.remark}</p>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <p class="t-center" style="color: #b4b0bf;">没有更多提现记录了</p>
    </article>
</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
</body>
</html>