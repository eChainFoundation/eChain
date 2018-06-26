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
				<h3 class="f-s4">Transaction List </h3>
			</div>
			<ul class="personCenter-list">
				<c:forEach items="${transactionList}" var="transaction">
					<li>
						<a class="t-box" style="height:40px;" onclick="getRecords('${transaction.logisticsCompanyId}',
						'${transaction.logisticsNo}')">
							<span>transactionNo:${transaction.transactionNo}</span><br/>
							<%-- <span>userBuyerId:${transaction.describeText.userBuyerId}</span><br/>
							<span>userBuyerName:${transaction.describeText.userBuyerName}</span><br/>
							<span>logisticsCompanyId:${transaction.describeText.logisticsCompanyId}</span><br/>
							<span>logisticsNo:${transaction.describeText.logisticsNo}</span><br/>
							<span>receivingAddressId:${transaction.describeText.receivingAddressId}</span><br/>
							<span>productName:${transaction.describeText.productName}</span><br/>
							<span>productPrice:${transaction.describeText.productPrice}</span> --%>
							<c:choose>
							   <c:when test="${transaction.transactionKey== '0' || transaction.transactionKey== null}"> 
							    <span>Not on the chain</span><br/>     
							   </c:when>
							   <c:otherwise>
							   	<span>On the chain</span><br/>
							   </c:otherwise>
							</c:choose>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</article>
	<c:if test="${transactionKey != '0'}"> 
		<article class="auto">
		    <span>transactionKey:${transactionKey}</span><br/>
			<button  type="button" class="f-s4" onclick="getMD5('${userId}','${dappId}');">Transfer</button> 
			<span id="md5" style="height: 30px;width: 300px;"></span>
		</article>
	</c:if>
	<article class="auto">
	    Notes：<br/>
		1.eChain packs the customers’ data and transfers it into MD5 value. Customers can go into the Etherscan and input their unique user ID to check the MD5 value transferred by their data.<br/>
		2.The MD5 value on this page and the value on the Etherscan should be the same.<br/>
		3.The following step is the way how to transferred your data into MD5 value.  
	</article>
</section>

<script type="text/javascript">
function getMD5(userId, dappId) {
	var param="userId="+userId+"&dappId="+dappId;
	$.ajax({
		url : "<%=basePath%>/transaction/check.do",
		data : param,
		type : "post", 
		cache : false,
		async:false,
		success: function(data){
		    if(data=="0"){
		    	return warning("Not Find This Dapp");
		    } else if(data == "1") {
		        //失败
		    	return warning("Not On The Chain");
		    } else {
		        //成功
		        $('#md5').text(data);
		        //return warning("Success");
		    }
		}
	});
}

function getRecords(logisticsCompanyId, logisticsNo){
	var param="logisticsCompanyId="+logisticsCompanyId+"&logisticsNo="+logisticsNo;
	location.href="<%=path%>/transaction/logistics_list.do?" + param;
}
</script>
</body>
</html>