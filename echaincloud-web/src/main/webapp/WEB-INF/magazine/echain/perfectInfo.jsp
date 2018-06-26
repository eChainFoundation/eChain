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
<link href="/static/css/upload/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<title>perfectInfo</title>
</head>
<body>
	<div class="header">
		<img src="/static/img/transaction/echian-logo.png" alt="">
	</div>
	<div class="infoBox">
		<div class="infoTop">
			<img src="/static/img/transaction/headerPic.png" alt="">
		</div>
		<div class="infoCon">
			<ul class="clearfix">
				<li>
	                <label>豆沙包订单号：</label>
	                <input type="text" id="orderId">
	            </li>
	            <li>
	                <label class="pOrderInfo">订单信息：</label>
	                <textarea name="" id="" cols="30" rows="10"></textarea>
	            </li>
			</ul>
			<p class="infoPBox">
				<input class="infoBtn" type="button" value="Upload" onclick="upload()">
				<input class="infoBtn" type="button" value="View" onclick="tatget()">
			</p>
		</div>

		<!--Key-->
		<p class="keyValue">
			
		</p>

		<!--Notes-->
		<div class="txtBox">
			<p style="text-align: left;">Notes：</p>
			<p>1.eChain packs the customers’ data and transfers it into MD5
				value. Customers can go into the Etherscan and input their unique
				user ID to check the MD5 value transferred by their data.</p>
			<p>2.The MD5 value on this page and the value on the Etherscan
				should be the same.</p>
			<p>3.The following step is the way how to transferred your data
				into MD5 value.</p>
		</div>

	</div>
	<script>
	function tatget(){
		var orderId= $('#orderId').val();
		if(orderId==null || orderId=="" || orderId ==undefined){
			alert("Please Input OrderId");
			return;
		}
		location.href = "<%=path%>/order/list";
	}
	
	function upload() {
		$(".keyValue").html("");
		var orderId= $('#orderId').val();
		
		if(orderId==null || orderId=="" || orderId ==undefined){
			alert("Please Input OrderId");
			return;
		}
		
		var param="orderId="+orderId;
		$.ajax({
			url : "<%=basePath%>/order/info.do",
			data : param,
			type : "post", 
			cache : false,
			async:false,
			dataType : "json",
			success: function(data){
				$(".keyValue").html('<span style="margin-right:6px;">Value:</span>' +
				'<span style="margin-right:20px;" id="md5"></span>' +
				'<a class="txtLi" style="-moz-user-select:none;-webkit-user-select:none;-ms-user-select:none;'+
			    '-khtml-user-select:none;user-select:none;" '+
			    'href="https://etherscan.io/token/0xf34cd2fd11233df8f90646ab658b03bfea98aa92#readContract" '+ 
			    'target="_blank">Checkit on the Etherscan &gt;</a>');
				$("#md5").text(data.hash);
			}
		});
	}
	</script>
</body>
</html>