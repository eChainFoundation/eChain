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
    <meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/swiper.css" rel="stylesheet" type="text/css">
    <link href="/static/font/iconfont.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/hotcss.js"></script>
    <title>wallet</title>
</head>
<body class="bg-white">
<header class="re-header">
    <div class="t-box header-top h-auto">
        <i class="iconfont icon-fanhui color-white f-icon-s2" onclick="javascript:history.go(-1)"></i>
        <h2 class="t-box-flex1 t-center color-white f-s4">钱包</h2>
    </div>

    <div class="headPicBox re-picBox">
            <span>
                <img src="/static/img/headPic.png" alt="">
            </span>
        <p class="color-white">${phone}</p>
        <p class="color-white re-font01">${total}<span>元</span></p>

    </div>
</header>
<section>
    <article>
        <ul class="wa-ul">
            <li style="cursor: pointer;" onclick="changePoints(1000)">
                <div class="wa-ul-box">
                    <span class="wa-buy">
                    </span>
                    <div class="wa-ul-child01"><span>1000</span>  ECP</div>
                    <div class="wa-ul-child02">${transfer.eth} ETH</div>
                </div>
                <span class="wa-money">10元</span>
            </li>

            <li style="cursor: pointer;"  onclick="changePoints(10000)">
                <div class="wa-ul-box">
                    <span class="wa-buy">
                    </span>
                    <div class="wa-ul-child01"><span>10000</span>  ECP</div>
                    <div class="wa-ul-child02">${transfer2.eth} ETH</div>
                </div>
                <span class="wa-money">100元</span>
            </li>
            <li style="cursor: pointer;" onclick="changePoints(100000)">
                <div class="wa-ul-box active">
                    <span class="wa-buy">
                        <img src="/static/img/wa-buy.png" alt="">
                    </span>

                    <div class="wa-ul-child01"><span>100000</span>  ECP</div>
                    <div class="wa-ul-child02">${transfer3.eth} ETH</div>
                </div>
                <span class="wa-money">1000元</span>
            </li>
        </ul>
        <p class="t-center">
            <input class="wa-btn" type="button" value="确认充值" style="cursor: pointer;" onclick="rechange()" id="sure">
        </p>
        <p class="t-center wa-txt">
            <span>汇率</span>
            <span>${transfer.rate}</span>
        </p>
        <p class="t-center" style="color: #b4b0bf;">已阅读并同意《用户充值协议》</p>
        <div class="assetBox">
            <p class="assetBoxP">资产</p>
            <c:forEach items="${list}" var="item">
                <div class="assetECPCon" style="cursor: pointer;" onclick="detail('${item.type}')">
                    <div class="assetCon01">
                        <img src="/static/img/${item.type}.png" alt="">
                        <span class="txtECP" >${item.type}</span>
                    </div>
                    <div class="assetCon02">
                        <p class="t-right assetConP01">${item.value}</p>
                        <p class="t-right assetConP02"><%--¥${item.rmb}--%></p>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${list.size() == 0}">
                <div class="assetECPCon" style="cursor: pointer;" onclick="detail('ECP')">
                    <div class="assetCon01">
                        <img src="/static/img/ECP.png" alt="">
                        <span class="txtECP" >ECP</span>
                    </div>
                    <div class="assetCon02">
                        <p class="t-right assetConP01">0</p>
                        <p class="t-right assetConP02"><%--¥0--%></p>
                    </div>
                </div>
                <div class="assetECPCon" style="cursor: pointer;" onclick="detail('ETH')">
                    <div class="assetCon01">
                        <img src="/static/img/ETH.png" alt="">
                        <span class="txtECP">ETH</span>
                    </div>
                    <div class="assetCon02">
                        <p class="t-right assetConP01">0</p>
                        <p class="t-right assetConP02"><%--¥0--%></p>
                    </div>
                </div>
            </c:if>
        </div>
    </article>
</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
<script>
    $(function () {
        $(".wa-ul-box").click(function () {
            var img = $("<img src='/static/img/wa-buy.png'>");
            $(this).addClass("active").parent().siblings().find(".wa-ul-box").removeClass("active");
            $(this).parent().find(".wa-buy").html(img);
            $(this).parent().siblings().find(".wa-buy").html("");
        })
    })

    var userId = getQueryString("userId");

    var points = 100000;

    function changePoints(p) {
        points = p
    }

    var flag = false;

    function rechange() {
        if(flag){
            return;
        }

        flag = true;

        var param = "userId=" + userId + "&points=" + points;
        $.ajax({
            url: "<%=basePath%>/rest/wallet/transferETH",
            data: param,
            type: "post",
            cache: false,
            async: false,
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    alert("充值成功");
                    flag = false;
                    window.location.href="<%=basePath%>/wallet?userId=" + userId;
                } else {
                    alert(data.msg);
                    flag = false;
                    window.location.href="<%=basePath%>/wallet/img?userId=" + userId + "&type=" + "ECP";
                }
            }
        });
    }

    function detail(type) {
        window.location.href = "<%=basePath%>/wallet/detail?userId=" + userId + "&type=" + type;
    }
</script>
</body>
</html>