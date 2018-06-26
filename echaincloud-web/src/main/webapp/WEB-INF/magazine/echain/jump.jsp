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
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/rem.js"></script>
<title>order</title>
<style>
    .imgBox02 {
        padding: 5%;
        background: #faf6fd;
        width: 90%;
        text-align: center;
        margin: 60px auto 0;
    }
    .imgBox02 img{
        width: 250px;
        height: 250px;
    }
    @media screen and (max-width: 768px){
        .orderTBody td {
            max-width: 250px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .imgBox02 img{
            width: 3rem;
            height: 3rem;
        }
        }
</style>
</head>
<body>
<div class="header">
    <img src="/static/img/transaction/echian-logo.png" alt="">
</div>
<div class="orderBox orderBox02">
	<div style="float:left;">
	    <table style="width:800px;">
	        <thead>
	        <tr>
	            <td><strong class="table-txt">Order</strong></td>
	            <td></td>
	        </tr>
	        </thead>
	        <tbody class="orderTBody">
	        <tr>
	            <td>Order No.</td>
	            <td>${order.orderId }</td>
	        </tr>
	        <tr>
	            <td>Block Height</td>
	            <td>${order.blockNo }</td>
	        </tr>
	        <tr>
	            <td>Transaction Hash</td>
	            <td>${order.transactionHash }</td>
	        </tr>
	        <tr>
	            <td>Key</td>
	            <td>${order.transactionKey }</td>
	        </tr>
	        <tr>
	            <td>Value</td>
	            <td>${order.describeMd5 }</td>
	        </tr>
	        </tbody>
	    </table>
    </div>
    <div  style="right;padding: 8%;width: 90%;text-align: center;margin: 80px auto 0;">
        <img src="/static/img/transaction/${order.orderId }.png" alt="" onclick="showOrder('${order.orderId }')" 
        style="cursor: pointer;width: 200px;height: 200px;"/>
        <p>扫一扫，查看产品标签</p>
    </div>
</div>

	<script>
	function showOrder(orderId){
		location.href='<%=path%>/order/detail.do?orderId=' + orderId;
	}
	</script>
</body>
</html>