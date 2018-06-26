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
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/swiper.css" rel="stylesheet" type="text/css">
    <link href="/static/font/iconfont.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/static/js/hotcss.js"></script>
    <script type="text/javascript" src="/static/js/common.js"></script>
    <title>Withdraw</title>
</head>
<body>
<header class="withdraw-header">
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui f-icon-s2 color-white"></i>
        <h2 class="t-box-flex1 color-white f-s4 mar-l">eChain Center</h2>
    </div>
</header>
<section class="login-box">
    <!-- 	<article class="login-header">
            <img src="../img/eMall/login-head.png" width="100%" />
        </article> -->
    <article class="auto">
        <ul class="login-ul">
            <li class="t-box">
                <div class="t-box-flex1">
                    <p class="pHeight">
                        <span class="fl c_989">ETH address</span>
                    </p>
                    <input type="text" id="EthAddress" value="${userWallet.wallet}"/>
                </div>
            </li>
            <input type="hidden" id="phoneNumber" value="${user.phoneNumber}"/>
            <li class="t-box">
                <div class="t-box-flex1">
                    <p class="c_989 pHeight">
                        <span class="fl c_989">ePoint Amount</span>
                        <span class="fr c_989" id="totalCount"></span>
                    </p>
                    <div class="pos-rel">
                        <input type="number" onmouseout="inputGetPrice(event)" id="ePointAmount"/>
                        <a class="pos-abs" style="right:0;top:0" onclick="getAll()">All</a>
                    </div>
                </div>
            </li>
            <li class="t-box">
                <div class="t-box-flex1">
                    <p class="pHeight">
                        <span class="c_989 fl">Fee</span>
                        <a class="fr">ePoint</a>
                    </p>
                    <p id="fee" class="pHeight"></p>
                </div>
            </li>
            <li class="t-box">
                <div class="t-box-flex1">
                    <p class="pHeight">
                        <span class="c_989 fl">Receive Amount</span>
                        <a class="fr">ePoint</a>
                    </p>
                    <p id="receiveAmount" class="pHeight"></p>
                </div>
            </li>
        </ul>
        <button type="button" class="btn btn-log bg-public-con" onclick="submit()" id="submitBtn">Withdraw</button>

    </article>

    <article class="withdraw-txt-box">
        <p>Tips</p>
        <ul>
            <li>1. The minimum withdraw amount：201 ePoint</li>
            <li>2. Please be sure the security of the computer and the website to prevent the falsifying and leakage.
            </li>
        </ul>
    </article>
</section>
<script>

    var totalCount = ${userPoints.nowPoints};//默认账号总余额
    var ePointAmount = null;//输入价格
    var fee = null;//中介费
    var receiveAmount = null;//应付价格
    $('#totalCount').text(totalCount);
    
    //价格公式
    function calPrice() {
        //如果为空
        if (ePointAmount == null) {
            $('#fee').text('');
            $('#receiveAmount').text('');
            return false;
        }
        if (ePointAmount <= 200) {
            $('#fee').text('');
            $('#receiveAmount').text('');
            return warning('The amount is less than the minimum, please enter again.');
        } else if (ePointAmount > totalCount) {
            $('#fee').text('');
            $('#receiveAmount').text('');
            return warning('The amount is over your deposit, please enter again.')
        }
        if (ePointAmount <= 200000) {
            fee = 200
        } else if (ePointAmount > 200000) {
            fee = ePointAmount * 0.001;
        }
        receiveAmount = ePointAmount - fee;
        $('#fee').text(fee);
        $('#receiveAmount').text(receiveAmount);
    }

    //输入获取
    function inputGetPrice(event) {
        event.target.value ? ePointAmount = event.target.value : ePointAmount = null;
        calPrice();
    }

    //点击all获取
    function getAll() {
        $('#ePointAmount').val(totalCount);
        ePointAmount = totalCount;
        calPrice();
    }

    //提交
    function submit() {
        var EthAddress = $('#EthAddress').val();
        var ePointAmount = $('#ePointAmount').val();
        if (!EthAddress) {
            return warning('Please fill in your ETH address.');
        } else if (!ePointAmount) {
            return warning('Please fill in your ePoint amount.');
        }
        
        var phoneNumber=$('#phoneNumber').val();
        var free=$('#fee').text();
        var all_points=$('#ePointAmount').val();
        
      //如果为空
        if (ePointAmount == null) {
            $('#fee').text('');
            $('#receiveAmount').text('');
            return false;
        }
        if (ePointAmount <= 200) {
            $('#fee').text('');
            $('#receiveAmount').text('');
            return warning('The amount is less than the minimum, please enter again.');
        } else if (ePointAmount > totalCount) {
            $('#fee').text('');
            $('#receiveAmount').text('');
            return warning('The amount is over your deposit, please enter again.')
        }
        
        var param="phoneNumber="+phoneNumber+"&wallet="+EthAddress
				+"&free="+free+"&all_points="+all_points;
        
        $.ajax({
            url : "<%=basePath%>/point/take_points.action",
            data : param,
            type : "post", 
            cache : false,
            async:false,
            dataType : "json",
            success: function(data){
                if(data=="0"){
                	return warning("Withdraw error");
                } else if(data == "1") {
                    //失败
                	return warning("Your credit balance less than ePoint amount");
                } else {
                    //成功
                    return warning("Success");
                }
                //$(this).attr("disabled",false);
            }
		});
	}

</script>
</body>
</html>