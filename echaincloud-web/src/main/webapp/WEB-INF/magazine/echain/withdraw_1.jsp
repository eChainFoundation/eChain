<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%
String path = "http://echain.echain.one";
String basePath = "http://echain.echain.one";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="telephone=no" name="format-detection" />
	<meta content="email=no" name="format-detection" />
	<link href="/static/css/style.css" rel="stylesheet" type="text/css">
	<link href="/static/css/swiper.css" rel="stylesheet" type="text/css">
	<link href="/static/font/iconfont.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/static/js/hotcss.js"></script>
	<title>eChain Center</title>
</head>
<body>
<header class="withdraw-header">
	<div class="t-box header-top h-auto">
		<i class="iconfont icon-fanhui icon-fanhui-js f-icon-s2 color-white"></i>
		<h2 class="t-box-flex1 color-white f-s4 mar-l">Registration</h2>
	</div>
</header>
<section class="login-box">
	<article class="auto">
		<ul class="login-ul">
			<li class="t-box">
				<div class="t-box-flex1">
					<input type="text" placeholder="ETH address" value=""/>
				</div>
			</li>
			<li class="t-box">
				<div class="t-box-flex1">
					<input type="text" placeholder="ePoint Amount" />
				</div>
			</li>

			<li class="t-box">
				<div class="t-box-flex1">
					<input type="text" placeholder="Free" />
				</div>
				<a>ePoint</a>
			</li>
			<li class="t-box">
				<div class="t-box-flex1">
					<input type="text" placeholder="Receive Amount" />
				</div>
				<a>ePoint</a>
			</li>
			
		</ul>
		<button type="button" class="btn eMall-btn-reg bg-public-con color-white">Withdraw</button>
		
	</article>

	<article class="withdraw-tips auto">
		<h4 class="f-s2">Tips</h4>
		<p class="color-gray mar-t">1. The minimum withdraw amount：1 ePoint</p>
		<p class="color-gray">2. Please be sure the security of the computer and the website to prevent the falsifying and leakage. </p>
	</article>
	
</section>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>

</body>
</html>