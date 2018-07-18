<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,viewport-fit=cover">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/swiper.css" rel="stylesheet" type="text/css">
    <link href="/static/font/iconfont.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/hotcss.js"></script>
    <title>提现</title>
</head>
<body class="bg-white">
<header>
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui f-icon-s2" onclick="javascript:history.go(-1)"></i>
        <h2 class="t-box-flex1 t-center f-s4">提现</h2>
    </div>
</header>
<section class="em-section">
    <article>
        <ul class="em-ul">
            <li class="dBox">
                <div>
                    <span>提现地址/账户</span>
                </div>
                <div style="width: 60%;">
                    <input type="text" placeholder="输入地址" id="address" value="${address}">
                </div>
            </li>
            <li class="dBox">
                <div>
                    <span>数量</span>
                </div>
                <div>
                    <input type="text" placeholder="提现数量" id="number">
                </div>
                <div>
                    <p>${wallet.type}</p>
                </div>
            </li>
            <li class="dBox" style="border: 0;">
                <div>
                    <span>可转额度</span>
                </div>
                <div>
                    <span style="color: #767676;">${wallet.value}</span>
                </div>
                <div onclick="withdrawAll()" style="cursor: pointer;"><p style="color: #501ef7;">全部提现</p></div>
            </li>

            <li class="dBox">
                <div><span>短信验证码</span></div>
                <div><input type="text" placeholder="短信验证码" id="verificationCode"></div>
                <div>
                    <p>
                        <s></s>
                        <span onclick="verCodeOn($(this))">GET</span>
                    </p>
                </div>
            </li>
        </ul>
        <p class="t-center">
            <input class="em-sure" type="button" value="确定" onclick="withdraw()">
        </p>
    </article>
    <article>
        <div class="txtBox">
            <p>提现须知</p>
            <p>支持金额：最低提现金额为100ECP</p>
            <p>提现金额：每日最高提现10000ECP，单笔最高提现10000ECP</p>
            <p>提现手续费：0.00009ECP</p>
        </div>
    </article>
</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
<script>
    $("#address").val('${address.wallet}');

    var userId = getQueryString("userId");
    var type = getQueryString("type");

    function check() {
        var number = $('#number').val();

        if (number == null || number == undefined || number.trim() == "") {
            alert("提现数量为空！");
            return false;
        }

        if (parseFloat(number) == '0') {
            alert("提现数量为0！");
            return false;
        }

        if (parseFloat(number) > parseFloat('${wallet.value}')) {
            alert("提现数量大于可转额度！");
            return false;
        }

        var to = $('#address').val();

        if(to == null || to == undefined || to.trim() == ""){
            alert("提现地址为空！");
            return false;
        }

        if('ECP'==type){
            //判断正整数
            var re = /^[1-9]+[0-9]*]*$/;
            if (!re.test(number)) {
                alert("请输入正整数");
                return false;
            }
        }

        return true;
    }

    function withdraw() {
        if(!check()){
            return;
        }

        var number = $('#number').val();
        var to = $('#address').val();
        var code = $("#verificationCode").val();

        if (code == null || code == undefined || code.trim() == "") {
            alert("验证码为空！");
            return;
        }

        var param = "userId=" + userId + "&type=" + type + "&number=" + number + "&to=" + to + "&verificationCode=" + code;
        $.ajax({
            url: "<%=basePath%>/rest/wallet/withdraw",
            data: param,
            type: "post",
            cache: false,
            async: false,
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    alert("成功");
                    window.location.href = "<%=basePath%>/wallet/withdrawHistory?userId=" + userId + "&type=" + type;
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    function withdrawAll() {
        $('#number').val('${wallet.value}')
    }

    function verCodeOn(_this) {
        if(!check()){
            return;
        }

        time($(_this))

        //获取验证码按钮
        $.ajax({
            url: "<%=path%>/rest/wallet/getVerificationCode",
            data: "phoneNumber=${phone}&country=${country}&name=" + type,
            type: "post",
            cache: false,
            async: false,
            dataType: "json",
            success: function (data) {
                flag = 1;
                if (data.code == 500) {
                    //$("#verificationCode").val(data);
                    $(".verCodeMask").remove();
                    alert("Failed, please try again");
                } else {
                    time($("#verCode"));
                    setTimeout(function () {
                        $(".verCodeMask").remove();
                    }, 60000);
                    console.info(data);
                }
                console.log("flag:" + flag);
            }
        });
    }

</script>
</body>
</html>