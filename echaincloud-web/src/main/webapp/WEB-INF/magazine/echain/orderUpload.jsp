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
	<title>Order upload</title>
</head>
<body>
<header class="orderUpload-header">
	<div class="t-box header-top h-auto">
		<i class="iconfont icon-fanhui icon-fanhui-js f-icon-s2 color-white"></i>
		<h2 class="t-box-flex1 color-white f-s4 mar-l"></h2>
		<a class="color-white f-s4">Join us</a>
	</div>
	<div class="orderU-tab">
		<ul name="${status }"class="auto t-box t-center f-s4">
		
			<li class="t-box-flex1 active">Success</li>
			<li class="t-box-flex1">Failded</li>
			<li class="t-box-flex1">Pending</li>
		</ul>
	</div>
</header>
<section class="orderU-box">
	<article class="orderU-success">
		<div class="orderU-head">
			<div class="auto t-box">
				<div class="t-box-flex1">Uploaded Orders</div>
				<div class="t-box-flex1 earned">Earned</div>
				<div class="t-box-flex1">Tracking ID</div>
			</div>
		</div>
		<div class="auto orderU-list">
				<ul>
					<c:forEach items="${succesList }" var="succestra">
						<li class="t-box border-b">
							<div class="t-box-flex1"><img src="<%=basePath%>/static/upload${succestra.transactionPicture}" /></div>
							<div class="t-box-flex1">
								<p class="f-s2 color-blue">
								<c:choose>
									<c:when test="${succestra.status==1 and succestra.logisticsNo!=null }">+50ePoint</c:when>
									<c:otherwise>+20ePoint</c:otherwise>
								</c:choose>
								</p>
								<p class="mar-t color-gray">${succestra.createTime }</p>
							</div>
							<div class="t-box-flex1 t-center">
							<c:choose>
									<c:when test="${succestra.logisticsNo!=null }">Matched</c:when>
									<c:when test="${succestra.logisticsNo==null }"><a class="match-txt" onclick="show('${succestra.id}');">Match</a></c:when>
							</c:choose>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
	</article>
	<article class="orderU-Failded" style="display: none">
		<div class="orderU-head">
			<div class="auto t-box">
				<div class="t-box-flex1 clearfix">
					<span class="fl">Failed orders</span>
					<span class="upload-time fr">Uploaded time</span>
				</div>

			</div>
		</div>
		<div class="auto orderU-list">
			<ul>
				<c:forEach items="${faildedList }" var="faildedtra">
					<li class="t-box border-b t-box-align">
						<div><img src="<%=basePath%>/static/upload${faildedtra.transactionPicture}" /></div>
						<div class="t-box-flex1 t-right color-gray">${faildedtra.createTime }</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</article>
	<article class="orderU-Checking" style="display: none">
		<div class="orderU-head">
			<div class="auto t-box">
				<div class="t-box-flex1 clearfix">
					<span class="fl">Pending orders</span>
					<span class="fr upload-time">Uploaded time</span>
				</div>
			</div>
		</div>
		<div class="auto orderU-list">
			<ul>
				<c:forEach items="${checkingList }" var="checkingtra">
					<li class="t-box border-b t-box-align">
						<div><img src="<%=basePath%>/static/upload${checkingtra.transactionPicture}" /></div>
						<div class="t-box-flex1 t-right color-gray">${checkingtra.createTime }</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</article>
	
</section>
<div class="ceng"></div>
<!--地址-->
<div class="address-box" id="touchbox">
	<ul class="auto">
		<li class="t-box border-b">
			<i class="iconfont f-icon-s2 icon-wuliu color-purple"></i>
			<div class="t-box-flex1 mar-r">
				<input type="text" id="companyName" placeholder="logistic company" disabled="disabled" class="f-s2" />
				<input type="hidden" id="company" name="company"/>
			</div>
			<i class="iconfont icon-jiantou_down f-icon-s2" onclick="_scroll('countrySelAll','country-scroll-all')"></i>
		</li>
		<li class="t-box border-b">
			<i class="iconfont f-icon-s2 icon-qiapian color-purple"></i>
			<div class="t-box-flex1">
				<input type="text" id="logisticsNo" name="logisticsNo" placeholder="Tracking ID" class="f-s2" />
				<input type="hidden" id="transactionId" name="transactionId"/>
			</div>
		</li>
		<li class="t-box">
			<div class="t-box-flex1" ><button type="button" class="f-s4 color-purple t-center" onclick="hide();">Back</button></div>
			<div class="t-box-flex1"><button type="button" class="f-s4 color-purple t-center" onclick="submit();">Submit</button></div>
		</li>
	</ul>
</div>
<!--country弹层-->
<div class="bg-white country-scroll-all" id="countrySelAll">
		<ul class="auto t-center f-s3">
		<c:forEach items="${lcList }" var="lc">
			<li>
				<p onclick="selectCompany(this,'${lc.id }');">${lc.companyName }</p>
			</li>
		</c:forEach>
		</ul>
</div>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/iscroll.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
<script type="text/javascript">
	function selectCompany(that,companyId) {
		$("#companyName").val($(that).html());
		$("#company").val(companyId);
		$(".ceng").trigger("click");
	}
	function submit() {
		var company=$("#company").val(),
		logisticsNo=$("#logisticsNo").val(),
		transactionId=$("#transactionId").val();
		var param={"companyId":company,"logisticsNo":logisticsNo,"transactionId":transactionId};
		$.ajax({  
			url : "<%=path%>/transaction/matche.do",   
			data : param,
			type : "post",  
			cache : false,
			dataType : "json",  
			success:  function(data){
				if(data=="1"){
					alert("Success！");
					location.reload();
				}else{
					alert("Failed！");
				}
			}
		});
	}
	function show(id) {
		$("#transactionId").val(id);
		$("#touchbox").show();
		$("#companyName").val("");
		$("#company").val("");
		$("#logisticsNo").val("");
	}
	function hide() {
		$("#transactionId").val("");
		$("#touchbox").hide();
	}
	$(function(){
		$('.orderU-tab li').click(function(){
			$('.orderU-tab li').removeClass('active');
			$(this).addClass('active');
			$('.orderU-box article').hide();
			$('.orderU-box article').eq($(this).index()).show();
		})
		
	})
</script>
</body>
</html>