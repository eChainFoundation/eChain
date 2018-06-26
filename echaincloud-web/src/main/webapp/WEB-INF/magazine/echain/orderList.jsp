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
<script type="text/javascript" src="/static/js/paging.min.js"></script>
<title>orderList</title>
</head>
<body>
	<div class="header">
		<img src="/static/img/transaction/echian-logo.png" alt="">
	</div>
	<div class="orderBox">
		<table>
			<thead>
				<tr>
					<td width="10%"><strong class="table-txt">Order</strong></td>
					<td width="75%"></td>
					<td width="10%"></td>
					<td width="5%"><span></span></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderList}" var="order">
					<tr>
						<td>${order.id}</td>
						<td>${order.orderId}</td>
						<td><span class="table-txt"
							onclick="showDetail('${order.orderId}')">detail</span></td>
						<td><i></i></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="total" value="${total}">
	</div>

	<!--分页-->
	<div class="box"></div>

	<script>
	function showDetail(orderId) {
		location.href='<%=path%>/order/jump.do?orderId=' + orderId;
    }
	
	//分页
    var total = $('#total').val();
    $('.box').paging({
        initPageNo: 1, // 初始页码
        totalPages: Math.ceil(total / 10),  //总页数
        totalCount: '合计' + total + '条数据', // 条目总数
        slideSpeed: 600, // 缓动速度。单位毫秒
        jump: true, //是否支持跳转
        callback: function(page) { // 回调函数
            // console.log(page);
            //clickChange(1);
        }
    });
	
	</script>
</body>
</html>