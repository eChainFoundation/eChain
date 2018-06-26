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
	<script type="text/javascript" src="<%=basePath%>/static/js/hotcss.js"></script>
	<title>eChain Center</title>
</head>
<body>
<header class="login-header">
	<div class="t-box header-top h-auto">
		<i class="iconfont icon-fanhui icon-fanhui-js f-icon-s2 color-white"></i>
		<h2 class="t-box-flex1 color-white f-s4 mar-l"></h2>
		<a class="f-s4 color-white">Join Us</a>
	</div>
</header>
<section>
	<article>
		<div class="ePoint-head t-box t-box-align">
			<div class="auto t-box-flex1 t-box t-box-align">
				<div class="f-s4"></div>
				<div class="t-box-flex1 t-right">
					<p class="f-s4">${getPoints }</p>
					<span class="color-gray">ePoints gained from ${ecDapp.dappName } </span>
				</div>
			</div>
		</div>
		<div class="ePoint-conent">
			<div class="auto">
				<ul>
					<c:forEach items="${points}" var="point">
						<li class="t-box t-box-align border-b">
							<div class="t-box-flex1">
								<h4 class="f-s4">${point.describeText }</h4>
								<p class="color-gray mar-t">${point.createTime }</p>
							</div>
							<div class="ePoint-num">
								${point.type == 1 ? "+" : "-" }<span>${point.points }</span>
							</div>
						</li>
					</c:forEach>
					<!-- <li class="t-box t-box-align border-b">
						<div class="t-box-flex1">
							<h4 class="f-s4">Invitation Reward</h4>
							<p class="color-gray mar-t">2018/4/8  10:16:23</p>
						</div>
						<div class="ePoint-num">
							+<span>20</span>
						</div>
					</li>
					<li class="t-box t-box-align border-b">
						<div class="t-box-flex1">
							<h4 class="f-s4">Invitation Reward</h4>
							<p class="color-gray mar-t">2018/4/8  10:16:23</p>
						</div>
						<div class="ePoint-num">
							+<span>20</span>
						</div>
					</li>
					<li class="t-box t-box-align border-b">
						<div class="t-box-flex1">
							<h4 class="f-s4">Invitation Reward</h4>
							<p class="color-gray mar-t">2018/4/8  10:16:23</p>
						</div>
						<div class="ePoint-num">
							+<span>20</span>
						</div>
					</li> -->
				</ul>
			</div>
		</div>
	</article>
</section>

<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/iscroll.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>

</body>
</html>