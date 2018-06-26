$(function() {
	$(".icon-fanhui-js").click(function(){
		history.back(-1);
	});
});
var global = {
    _code:""
};
// 公共input 表单验证方法 判断不为空
function verify(id, value,placeholder,$) {
	var name = $(id).val();
	if (name == '') {
		alert(value)
		return false;
	}else{
		return true;
	}
	$(id).focus(function() {
		$(id).attr('placeholder', placeholder);
	})
	
}

// 手机号正则验证(公共方法，只需传入id即可)
function phoneValidation(id,$) {
	var rePhone = /^1[3|4|5|7|8][0-9]\d{4,8}$/
	var usePhone = $(id).val();
	if (usePhone == '') {
		alert( '手机号不能为空!');
		return false;
	} else if (rePhone.test(usePhone)) {
		$(id).val(usePhone);
		return true
	} else {
		$(id).val('');
		alert('请输入正确的手机号!');
		return false;
	}
	$(id).focus(function() {
		$(id).attr('placeholder', '投保人手机号码');
	})
	
}

// 获取验证码计时器
var wait = 60;
var timer = null;
function time(o) {
	if (wait == 0) {
		clearTimeout(timer);
		timer = null;
		wait = 60;
		o.text("GET");
		o.css({'color':'#5821ff'})
	} else {
		o.text("GET AGAIN(" + (wait--) + ")");
		o.css({'color':'#999'})
		timer = setTimeout(function() {
			time(o);
		}, 1000);
	}
}

//获取url的参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}
//原型继承Date
  	Date.prototype.format = function(format){ 
		var nextDate = new Date(new Date().getTime() - 24*60*60*1000- 24*60*60*1000); //钱三天
		console.log(nextDate)
		var that=nextDate;//之前为this,指当前时间
		var o = { 
			"M+" : that.getMonth()+1, //month 
			"d+" : that.getDate(), //day 
			"h+" : that.getHours(), //hour 
			"m+" : that.getMinutes(), //minute 
			"s+" : that.getSeconds(), //second 
			"q+" : Math.floor((that.getMonth()+3)/3), //quarter 
			"S" : that.getMilliseconds() //millisecond 
		} 
		console.log(o)
		if(/(y+)/.test(format)) { 
			format = format.replace(RegExp.$1, (that.getFullYear()+"").substr(4 - RegExp.$1.length)); 
			console.log(format)
		} 

		for(var k in o) { 
			if(new RegExp("("+ k +")").test(format)) { 
				format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
			} 
		} 
		return format; 
	} 
//时间判定
function timeCompare(selTime,time){
	var s = ((new Date(selTime.replace(/-/g,"\/"))) - (new Date(time.replace(/-/g,"\/"))));
	var day=s/1000/60/60/24;
	return day;
}
//提示
function warning(val){
	warning(val,5000);
}
function warning(val,time){
	var _html = '<div class="echain-warning f-s4"><p class="color-white">'+val+'</p></div>'
	$('body').append(_html);
	setTimeout(function(){
		$('.echain-warning').fadeOut('slow',function(){$('.echain-warning').remove()})
	},time)
}
//滚动
function _scroll(id,_class){
	$('.ceng').show();
	$('.ceng').on('touchmove',function(e){e.preventDefault();},false);
	new iScroll(id);
	$('.'+_class).animate({
		bottom:0
	})
}
//pay-order-close
function payOrderClose(_class){
	$('.'+_class).animate({
		bottom:-9.3866666667+'rem'
	});
	$('.ceng').hide();
	$('.ceng').on('touchmove',function(e){e.preventDefault();},true);
}

//personal order
function orderList(that){
	if(that.attr('data-status') === '0'){
		that.parent('.personal-order-a').siblings('.personal-order-er').slideDown();
		that.attr('data-status','1')
	}else{
		that.parent('.personal-order-a').siblings('.personal-order-er').slideUp();
		that.attr('data-status','0')
	}
}

function _close(){
	console.log('执行close');
	$('.country-scroll-box').animate({
			bottom:-4.1813333333+'rem'
		});
		$('.pay-scroll-box').animate({
			bottom:-9.3866666667+'rem'
		});
		$('.country-scroll-all').animate({
			bottom:-12.544+'rem'
		},100);

		$('.ceng').hide();
		$('.ceng').on('touchmove',function(e){e.preventDefault();},true);
}
(function(){
	$('.ceng').click(function(){
		_close();
	})
})();

// canvas验证码
/**生成一个随机数**/
function randomNum(min,max){
    return Math.floor( Math.random()*(max-min)+min);
}
/**生成一个随机色**/
function randomColor(min,max){
    var r = randomNum(min,max);
    var g = randomNum(min,max);
    var b = randomNum(min,max);
    return "rgb("+r+","+g+","+b+")";
}
drawPic();
document.getElementsByClassName("changeImg")[0].onclick = function(e){
    global._code ="";
    drawPic();
};

/**绘制验证码图片**/
function drawPic(){
    var canvas=document.getElementById("canvas");
    var width=canvas.width;
    var height=canvas.height;
    var ctx = canvas.getContext('2d');
    ctx.textBaseline = 'bottom';
    /**绘制背景色**/
    ctx.fillStyle = "#fff";
    ctx.fillRect(0,0,width,height);
    /**绘制文字**/
    var str = 'ABCEFGHJKLMNOPQRSTUVWXYabcdefghijkmnpqrstuvwxyz';
    for(var i=0; i<4; i++){
        var txt = str[randomNum(0,str.length)];
        global._code +=txt;
        ctx.fillStyle = randomColor(50,160);  //随机生成字体颜色
        ctx.font = '30px SimHei'; //生成字体大小
        var x = 10+i*25;
        var y = 35;
        //修改坐标原点和旋转角度
        ctx.translate(x,y);
        ctx.fillText(txt, 0,0);
        //恢复坐标原点和旋转角度
        ctx.translate(-x,-y);
    }
    /**绘制干扰点**/
    for(var j=0; j<20; j++){
        ctx.fillStyle = randomColor(0,255);
        ctx.beginPath();
        ctx.arc(randomNum(0,width),randomNum(0,height), 1, 0, 2*Math.PI);
        ctx.fill();
    }
}

// 判断验证码是否正确
function codeCheckout(that) {
    if(that.val().toLocaleLowerCase() != global._code.toLocaleLowerCase()) {
        global._code="";
        drawPic();
        that.val("");
        alert("验证码输入错误！");
    } else {
        //...
    }
}
// 贡献者、投资机构ceng
$(".sub-pop").on("click",function () {
    $(".ceng").fadeIn();
});
$('.off-pop').on('click',function(ev){var ev = event || ev; ev.stopPropagation();});
$(".off-back").on("click",function () {
    $(".ceng").fadeOut();
});