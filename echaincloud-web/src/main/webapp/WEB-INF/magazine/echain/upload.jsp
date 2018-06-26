<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <meta
            content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <link href="<%=basePath%>/static/css/style.css" rel="stylesheet"
          type="text/css">
    <link href="<%=basePath%>/static/css/style.scss" rel="stylesheet"
          type="text/css">
    <link href="<%=basePath%>/static/css/animation.css" rel="stylesheet"
          type="text/css">
    <link href="<%=basePath%>/static/css/swiper.css" rel="stylesheet"
          type="text/css">
    <link href="<%=basePath%>/static/font/iconfont.css" rel="stylesheet"
          type="text/css">
    <script type="text/javascript" src="<%=basePath%>/static/js/hotcss.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
    <title>eChain center</title>
</head>
<body class="bg-index-con">


<section>
    <article class="index-header">
        <div class="icon-box" onclick="jumpBack()">
            <i class="iconfont icon-fanhui f-icon-s2 color-white"></i>
        </div>
        <img src="<%=basePath%>/static/img/earth2.png" width="100%"
             class="pos-abs earth"/> <img
            src="<%=basePath%>/static/img/echain-logo.png"
            class="pos-abs echain-logo"/>
        <div class="pos-abs index-about index-about02">
            <a href="">Join us <i class="iconfont f-icon-s1 icon-youjiantou"></i></a>
        </div>
        <p class="upload-prompt pos-abs">Please <u></u>upload your order details</p>
    </article>
    <article class="upload-con">
        <div class="auto color-white f-s2">
            <p>1.The order you upload must be complete and clear.</p>
            <p>2.The following information must be included: Order number,
                Price, Date.</p>
        </div>
        <!-- <button type="button" class="bg-public-con color-white upload-btn f-s4">Upload</button> -->
        <form action="<%=path%>/transaction/upload.do"
              enctype="multipart/form-data" method="post" onsubmit="check();">
            <div class="file-box">
                <span class="upload-btn bg-public-con color-white f-s4">Upload</span>
                <input class="upload-file" type="file" name="file"
                       accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"
                       class="f-s4" onchange="fileChange(this);">
            </div>
            <input type="hidden" name="userId" value="${userId}">
            <div class="img-box">
                <img id="src">
            </div>
            <input type="submit" class="submit-btn bg-public-con color-white f-s4" value="Submit">
        </form>
    </article>
</section>
<script type="text/javascript"
        src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>


<script type="text/javascript"
        src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
<script>

    $(function () {
        $(".submit-btn").click(function () {
            if ($(".img-box").is(":hidden")) {
                alert("请先上传图片");
                return false;
            }
        })
    });
    var isSubmint = false;

    function fileChange(target, id) {
        var filetypes = [".jpg", ".png", ".gif", ".jpeg", ".svg"];
        var filepath = target.value;
        if (filepath) {
            var isnext = false;
            var fileend = filepath.substring(filepath.indexOf("."));
            if (filetypes && filetypes.length > 0) {
                for (var i = 0; i < filetypes.length; i++) {
                    if (filetypes[i] == fileend) {
                        isnext = true;
                        isSubmint = true;
                        var file = target.files.item(0);
                        console.info(file);
                        var freader = new FileReader();
                        freader.readAsDataURL(file);
                        freader.onload = function (e) {
                            $("#src").attr("src", e.target.result);
                            $(".img-box").show();
                        }
                        break;
                    }
                }
            }
            if (!isnext) {
                warning("不接受此文件类型");
                target.value = "";
                isSubmint = false;
                return false;
            }
        } else {
            return false;
        }
    }

    function check() {
        console.info(isSubmint);
        return isSubmint;
    }

    function jumpBack() {
        var phoneNumber = getQueryString("phoneNumber"),
            type = getQueryString("type");
        location.href = "<%=path%>/user/user_detail.do?phoneNumber=" + phoneNumber + "&type=" + type;
    }

</script>
</body>
</html>