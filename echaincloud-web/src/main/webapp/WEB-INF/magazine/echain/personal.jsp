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

		<div class="t-box t-box-align personal-header-box">
			<div class="personal-header-img mar-r"><img src="<%=basePath%>/static/img/header-img.png" /></div>
			<div class="t-box-flex1 f-s4 color-white">
				<p>${userName }</p>
			</div>
			<div class="personal-point f-s2 color-white t-box t-box-align">
				<img src="<%=basePath%>/static/img/eMall/personal-icon.png" />
				<div class="t-box-flex1">ePoint:<span>${points }</span></div>
			</div>
		</div>
</header>
<section>
	<article>
		<div class="auto">
			<div class="list-height t-box">
				<i class="iconfont icon-tubiaolunkuo- mar-r color-purple f-icon-s2"></i>
				<h3 class="f-s4">dApps you are using </h3>
			</div>
			<ul class="personCenter-list">
				<c:forEach items="${appPoints }" var="appPoint">
					<li>
						<a class="t-box">
							<div class="presonC-img"><img src="<%=basePath%>${appPoint.key.dappLogo}" /></div>
							<div class="t-box-flex1 t-right">
								<p class="color-gray personC-prompt">You have earned <span class="color-block">${appPoint.value } ePoints</span></p>
								<div class="personC-check t-box t-box-align">
									<%-- <button class="f-s4" onclick="transcation('${userId}','${appPoint.key.id }')">Detail</button> --%>
									<p class="t-box-flex1 t-right f-s4" onclick="transcation('${userId}','${appPoint.key.id }')">Detail</p>
									<i class="iconfont icon-youjiantou"></i>
									<p class="t-box-flex1 t-right f-s4" onclick="getAppPoints('${userId}','${appPoint.key.id }');">Check</p>
									<i class="iconfont icon-youjiantou"></i>
								</div>
							</div>
						</a>
					</li>
				</c:forEach>
				<%-- <li>
					<a class="t-box">
						<div class="presonC-img"><img src="<%=basePath%>/static/img/presonal-center-icon1.png" /></div>
						<div class="t-box-flex1 t-right">
							<p class="color-gray personC-prompt">You have earned <span class="color-block">100 ePoints</span></p>
							<div class="personC-check t-box t-box-align">
								<p class="t-box-flex1 t-right f-s4">check</p>
								<i class="iconfont icon-youjiantou"></i>
							</div>
						</div>
					</a>
				</li>
				<li>
					<a class="t-box">
						<div class="presonC-img"><img src="<%=basePath%>/static/img/presonal-center-icon2.png" /></div>
						<div class="t-box-flex1 t-right">
							<p class="color-gray personC-prompt">You have earned <span class="color-block">100 ePoints</span></p>
							<div class="personC-check t-box t-box-align">
								<p class="t-box-flex1 t-right f-s4">check</p>
								<i class="iconfont icon-youjiantou"></i>
							</div>
						</div>
					</a>
				</li>
				<li>
					<a class="t-box">
						<div class="presonC-img"><img src="<%=basePath%>/static/img/presonal-center-icon3.png" /></div>
						<div class="t-box-flex1 t-right">
							<p class="color-gray personC-prompt">You have earned <span class="color-block">100 ePoints</span></p>
							<div class="personC-check t-box t-box-align">
								<p class="t-box-flex1 t-right f-s4">check</p>
								<i class="iconfont icon-youjiantou"></i>
							</div>
						</div>
					</a>
				</li> --%>
			</ul>
		</div>
	</article>
	<article class="auto">
		<div class="t-box personC-upload">
			<div class="t-box-flex1">
				<a class="bg-public-con f-s4 color-white" onclick="jumpHref()">Upload orders to earn ePoints</a>
			</div>
			<div>
				<button type="button" class="f-s4" onclick="uploadList('${userId}');">Check</button>
			</div>
		</div>
	</article>
</section>

<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>

</body>
</html>
<script type="text/javascript">
function uploadList(userId) {
	location.href="<%=path%>/transaction/"+userId+"/uploadList.do";
}

function getAppPoints(userId,appId) {
	var body = $(document.body),
	form = $("<form method='post'></form>"),
	input;
	form.attr({"action":"<%=path%>/point/details.do"});
	$.each({"userId":userId,"appId":appId},function(key,value){
	    input = $("<input type='hidden'>");
	    input.attr({"name":key});
	    input.val(value);
	    form.append(input);
	});
	form.appendTo(document.body);
    form.submit();
    document.body.removeChild(form[0]);
}


function jumpHref() {
	var phoneNumber = getQueryString("phoneNumber"),
        type = getQueryString("type");
	location.href = "<%=path%>/user/${userId}/upload.do?phoneNumber=" + phoneNumber+"&type=" + type;
}

function transcation(userId, dappId) {
	// 校验是否存在交易记录
	var param="userId="+userId+"&dappId="+dappId;
	$.ajax({
		url : "<%=basePath%>/transaction/check_order.do",
		data : param,
		type : "post", 
		cache : false,
		async:false,
		success: function(data){
		    if(data=="0"){
		    	return warning("Not Find This Dapp");
		    } else if(data == "1") {
		        //失败
		    	return warning("No Order");
		    	//location.href="<%=path%>/transaction/transaction_list.do?userId=" + userId +"&dappId=" + dappId;
		    } else {
		        //成功
		    	location.href="<%=path%>/transaction/transaction_list.do?userId=" + userId +"&dappId=" + dappId;
		    }
		}
	});
}
</script>
