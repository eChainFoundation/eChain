<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%
/* String path = "http://echain.echain.one";
String basePath = "http://echain.echain.one"; */

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<link href="<%=basePath%>/static/css/style.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>/static/css/swiper.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>/static/font/iconfont.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/hotcss.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
	<title>Personal Center</title>
</head>
<body style="background:#fff;">
<header class="eChain-personal-header bg-white border-b">
	<div class="t-box header-top h-auto">
		<i class="iconfont icon-fanhui icon-fanhui-js f-icon-s2 color-white"></i>
		<h2 class="t-box-flex1 f-s4 mar-l color-white"></h2>
		<a class="mar-r color-white f-s4">Withdraw</a>
		<a class="color-white f-s4">Join us</a>
	</div>
</header>
<section>
	<article>
		<div class="auto">
			<div class="list-height t-box">
				<i class="iconfont icon-tubiaolunkuo- mar-r color-purple f-icon-s2"></i>
				<h3 class="f-s4">Logistics List </h3>
			</div>
			<ul class="personCenter-list">
				<c:forEach items="${recordList}" var="record">
					<li>
						<a class="t-box" style="height:110px;">
							<span>logisticsNo:${record.logisticsText.logisticsNo}</span><br/>
							<%-- <span>logisticsCompanyId:${record.logisticsText.logisticsCompanyId}</span><br/> --%>
							<%-- <span>transactionId:${record.logisticsText.transactionId}</span><br/> --%>
							<span>productName:${record.logisticsText.productName}</span><br/>
							<%-- <span>optionerName:${record.logisticsText.optionerName}</span><br/> --%>
							<span>optionContent:${record.logisticsText.optionContent}</span><br/>
							<span>createTime:${record.logisticsText.createTime}</span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</article>
</section>

<script type="text/javascript">

</script>
</body>
</html>