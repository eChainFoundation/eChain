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
	<meta charset="utf-8">
	  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
	<title></title>
</head>
<style>
	@media (min-width: 1200px){
		.container {
			max-width: 1500px;
		}
	}
	.container table th,.container table td{
		text-align: center;
	}
	.container tbody img{
		width: 300px;
		height: 200px;
	}
	.container tbody td{
		line-height: 200px;
	}
</style>
<body>
<div class="container">
	<table class="table table-striped">
	    <thead>
	      <tr>
	      	<th>编号</th>
	        <th>订单</th>
	        <th>用户手机号</th>
	        <th>是否通过</th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach items="${transaction }" var="tr" varStatus="statu">
	    	<tr>
	    		<td>${statu.count }</td>
		        <td><img src="<%=basePath %>/static/upload/${tr.transactionPicture }"></td>
		        <td>${users[tr.userId].phoneNumber }</td>
		        <td>
		        	<button type="button" class="btn btn-primary" onclick="checkTransaction('${tr.id }',true,$(this));">是</button>
		        	<button type="button" class="btn btn-danger" onclick="checkTransaction('${tr.id }',false,$(this));">否</button>
		        </td>
		      </tr>
	    </c:forEach>
	    </tbody>
  	</table>
  	  <ul class="pagination justify-content-end">
	    <li class="page-item"><a class="page-link" onclick="goIndex('${index-1}')">Previous</a></li>
	    <li class="page-item"><a class="page-link" onclick="goIndex('1')">1</a></li>
	    <!-- <li class="page-item active"><a class="page-link" href="#">2</a></li>
	    <li class="page-item"><a class="page-link" href="#">3</a></li> -->
	    <li class="page-item"><a class="page-link" onclick="goIndex('${index+1}')">Next</a></li>
	  </ul>
</div>
<script type="text/javascript" src="/static/js/jquery-1.11.3.min.js"></script>
</body>
</html>
<script type="text/javascript">
function goIndex(num) {
	if(num<=0){
		num=1;
	}
	location.href="<%=path%>/transaction/upload_list.do?index="+num;
}
var flag = 1;
function checkTransaction(id,isCheck,that) {
	var param={"id":id,"check":isCheck};
    if(flag == 1){
        flag = 0;
        that.attr("disabled",true);
        $.ajax({
            url : "<%=path%>/transaction/check_upload.do",
            data : param,
            type : "post",
            cache : false,
            async:false,
            success:  function(data){
                flag = 1;
                console.log(flag);
                if(data=="1"){
                    alert("该订单审核成功");
                    location.reload();//刷新当前页
                }else{
                	alert("该订单未通过审核");
                	location.reload();//刷新当前页
                    //warning("审核异常，请手动处理.");
                }
            }
        });
    }
}

// 鼠标移动图片放大
$(function(){
    $('.container img').mouseover(function(){
        var wValue=1.5 * $(this).width();
        var hValue=3 * $(this).height();
        $(this).stop().animate({width: wValue,
            height: hValue,
            left:("-"+(0.5 * $(this).width())/2),
            top:("-"+(0.5 * $(this).height())/2)}, 1000);
    }).mouseleave(function(){
        $(this).stop().animate({width: "300",
            height: "200",
            left:"0px",
            top:"0px"}, 1000 );
    });
});


</script>