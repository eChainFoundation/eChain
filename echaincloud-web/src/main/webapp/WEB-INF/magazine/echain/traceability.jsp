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
    <meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="telephone=no" name="format-detection" />
    <meta content="email=no" name="format-detection" />
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/font/iconfont.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/hotcss.js"></script>
    <title>Traceability</title>
</head>
<body>
<header class="trace-header">
    <div class="t-box header-top h-auto">
        <!-- <i class="iconfont icon-fanhui f-icon-s2 color-white" onclick="back()"></i> -->
        <h2 class="t-box-flex1 color-white f-s4 t-center">投保信息</h2>
    </div>
    <div class="trace-imgBox">
        <img src="/static/img/genuineinsurance.png" alt="">
        <span><a href="https://etherscan.io/tx/${order.transactionHash}" target="_blank">进入以太坊网络&gt;</a></span>
        <p>本订单产品由中华联合保险提供正品保证！</p>
    </div>
</header>
<section class="trace-box">
<article class="trace-box01">
    <div>
        <p class="box01-p">
            <i></i>
            <span>投保信息</span>
        </p>
        <ul class="box01-ul">
            <li>
                <span>订单号：</span>
                <span>${vo.orderId}</span>
            </li>
            <li>
                <span>保障期限：</span>
                <span>90天</span>
            </li>
            <li>
                <span>被保险人：</span>
                <span>${vo.userName}</span>
            </li>
        </ul>
    </div>
    <div class="box01-div">
        <p>保障责任</p>
        <ul class="box01-div-ul">
            <li>
                <span>正品保证 </span>
                <p>假一赔一，最高赔付1万元</p>
            </li>
        </ul>
        <p class="box01-div-p">
            <a href="https://etherscan.io/token/0xf34cd2fd11233df8f90646ab658b03bfea98aa92#readContract">
                <img src="/static/img/testBtn.png" alt="">
            </a>
        </p>
    </div>
</article>
<article class="trace-box02">
    <p class="box01-p">
        <i></i>
        <span>系统支持方</span>
    </p>
    <div class="box02-img">
        <img src="/static/img/linkBox.png" alt="">
    </div>
</article>

</section>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/js/iscroll.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
<script type="text/javascript">
function back(){
	location.href='<%=path%>/order/list';
}
</script>
</body>
</html>