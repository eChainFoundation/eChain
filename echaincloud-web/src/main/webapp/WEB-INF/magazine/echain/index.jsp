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
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<link href="css/animation.css" rel="stylesheet" type="text/css">
	<link href="css/swiper.css" rel="stylesheet" type="text/css">
	<link href="font/iconfont.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/hotcss.js"></script>
	<title>eChain.one</title>
</head>
<body class="bg-index-con">
<section>
	<article class="index-header">
		<img src="img/earth.png" width="100%" class="pos-abs earth" />
		<img src="img/echain-logo.png" class="pos-abs echain-logo" />
		<div class="pos-abs index-about">
			<a href="">About <i></i></a>
		</div>
		<p class="echain-prompt pos-abs">E-commerce dApps found on echain Protocol</p>
	</article>
	<article class="index-con">
		<img src="img/index-solid.png" width="100%" class="index-solid" />
		<ul class="pos-rel mall-ul clearfix">
			<li class="qMall"><a><img src="img/qMall.png" /></a></li>
			<li class="eMall"><a><img src="img/eMall.png" /></a></li>
			<li class="nMall"><a><img src="img/nMall.png" /></a></li>
		</ul>
	</article>
</section>	


<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script>




</script>
</body>
</html>