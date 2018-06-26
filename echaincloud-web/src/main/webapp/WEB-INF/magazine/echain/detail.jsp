<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>
<%
	/* String path = "http://echain.echain.one";
	String basePath = "http://echain.echain.one"; */

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="telephone=no" name="format-detection" />
<meta content="email=no" name="format-detection" />
<link href="/static/css/upload/style.css" rel="stylesheet"
	type="text/css">
<link href="/static/css/upload/paging.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<title>orderDetail</title>
</head>
<body>
	<div class="header">
    <img src="/static/img/transaction/echian-logo.png" alt="">
</div>
<div class="infoBox">


    <div class="infoTop">
        <img src="/static/img/transaction/headerPic.png" alt="">
    </div>

    <ul class="listBox">
        <li>
            <span>豆沙包订单号：</span>
            <p>${order.orderId}</p>
        </li>
        <li>
            <span>姓名：</span>
            <p>${order.userName}</p>
        </li>
        <li>
            <span>手机号：</span>
            <p>${order.phoneNumber}</p>
        </li>
        <li>
            <span>产品ID：</span>
            <p>${order.productId}</p>
        </li>
        <li>
            <span>产品名称：</span>
            <p>${order.productName}</p>
        </li>
        <li>
            <span>保费：</span>
            <p>${order.premium}</p>
        </li>
        <li>
            <span>商品订单时间：</span>
            <p>${order.orderTime}</p>
        </li>
        <li>
            <span>投保时间：</span>
            <p>${order.insureTime}</p>
        </li>
        <li>
            <span>Source：</span>
            <p>${order.source}</p>
        </li>
        <li>
            <span>PurchasOrderNo：</span>
            <p>${order.purchasOrderNo}</p>
        </li>
        <li>
            <span>PurchasOrderAmount：</span>
            <p>${order.purchasOrderAmount}</p>
        </li>
        <li>
            <span>Category：</span>
            <p>${order.category}</p>
        </li>
        <li>
            <span>ExpressNo：</span>
            <p>${order.expressNo}</p>
        </li>
        <li>
            <span>ExpressCompanyName：</span>
            <p>${order.expressCompanyName}</p>
        </li>
        <li>
            <span>ExpressChannel：</span>
            <p>${order.expressChannel}</p>
        </li>
        <li>
            <span>ExpressTime：</span>
            <p>${order.expressTime}</p>
        </li>
        <li>
            <span>Brand：</span>
            <p>${order.brand}</p>
        </li>
    </ul>
</div>
	<script>
		
	</script>
</body>
</html>