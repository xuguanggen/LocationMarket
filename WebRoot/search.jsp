<%@page import="com.xgg.utils.Tool"%>
<%@page import="com.xgg.beans.RecordBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>上海联通位置营销</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <link rel="shortcut icon" href="images/favicon.ico"/>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=8ce5a9e0da0203b74b18b456f8f7daf1&plugin=AMap.MouseTool"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>    
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    
    <link rel="stylesheet" type="text/css" href="css/jquery.datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/nav.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-switch.min.css"/>
    <link href="css/bootstrap-spinner.css" rel="stylesheet">    
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>

<div class="headbar">
	位置营销展示平台
</div>

<div id="contentcontainer">
	<div class="leftsidebar_box">	
		<dl class="custom">
			<dt>客户发现<img src="images/left/select_xl01.png"></dt>
			<dd class="first_dd"><a href="#">客户管理</a></dd>
			<dd><a href="#">试用/成交客户管理</a></dd>
			<dd><a href="#">未成交客户管理</a></dd>
			<dd><a href="#">即将到期客户管理</a></dd>
		</dl>
		<hr style="FILTER: alpha(opacity=100,finishopacity=0,style=1)"/>
		<dl class="statistics">
			<dt>位置发现<img src="images/left/select_xl01.png"></dt>
		</dl>	
	</div>
	
	
	<div class="part1">		
			<div class="searchmap">
				<table border=1px width=100% height=100%>
					<tr><th height="10%"><div class='bar'><img src="images/map_pin_green.png">地图</div></th></tr>
					<tr><td width="90%" height="95%"><div id="container" style="position: relative; "></div></td><tr>
				</table>
			</div>
		<form id="searchform" name="searchform">
			<div class="timespace">
				<div class="spacediv">
					<table border=1px width=100% height=100%>
						<tr><th height="25%" colspan="3"><div class='bar'>空间范围</div></th></tr>
						<tr>
							<td height="25%">左上角:</td>
							<td><input id="lefttoplng" type="text" name="lefttoplng" placeholder="经度" class="lonlat"/></td>
							<td><input id="lefttoplat" type="text" name="lefttoplat" placeholder="纬度" class="lonlat"/></td>
						</tr>
						<tr>
							<td height="25%">右下角:</td>
							<td><input id="rightbottomlng" type="text" name="rightbottomlng" placeholder="经度" class="lonlat"/></td>
							<td><input id="rightbottomlat" type="text" name="rightbottomlat" placeholder="纬度" class="lonlat"/></td>
						</tr>
					</table>
				</div>
				<div class="timediv">
					<table border=1px width=100% height=100%>
						<tr><th height="25%" colspan="3"><div class='bar'>时间范围</div></th></tr>
						<tr>
							<td height="25%">起始时间：</td>
							<td width=80%><input type="text" id="StartTime" name="StartTime" class="time"/></td>
						</tr>
						<tr>
							<td height="25%">终止时间：</td>
							<td><input type="text" id="EndTime" name="EndTime" class="time"/></td>
						</tr>
					</table>
				</div>
				
				<div class="submit">
					<table width=80% height=80%>
						<tr>
							<td valign="middle">TopN：</td>
							<td width="85%" height="20%" colspan="2">
								<div class="input-group spinner" data-trigger="spinner" style='height:100%;width:50%;'>
          							<input type="text"  id="topn" name="topn" class="form-control" value="500" data-max="2000" data-min="0" data-step="100" style='height:136%;'>
         								 <div class="input-group-addon">
									            <a href="javascript:;" class="spin-up" data-spin="up"><i class="icon-sort-up"></i></a>
									            <a href="javascript:;" class="spin-down" data-spin="down"><i class="icon-sort-down"></i></a>
								          </div>
								</div>								
							</td>
						</tr>
						<tr>
							<td width="14%"><input name="my_checkbox" type="checkbox" checked="true"/></td>
							<td width="43%"><center><input class="btn btn-primary" type="reset" value="重置输入" onClick="clearInput()"/></center></td>
							<td width="43%"><input class="btn btn-primary" type="button" value="开始查询" onClick="submitToBackEnd()"/></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>	

	<div>
		<div id="showPart" class="showPart" style="display:none;">
			<div style="width:50%;height:100%;float:left;" class="bar">
				<table border="1" width="100%" height="100%">
				  <tr>
				    <th><img src="images/map_pin_red.png">热力图</th>
				  </tr>
				  <tr>
				    <td width="100%" height="90%"><div id="heatmap" class="heatmap"></div></td>
				   </tr>
				 </table>
			</div>
			<div style="width:49%;height:100%;margin:0 auto;vertical-align:top;float:right;">
				<table border="1" height="50%" width="100%">
					<tr>
						<th align="center"><div class="bar"><img src="images/phone.png">终端类型</div></th>
						<th align="center"><div class="bar"><img src="images/gender.jpg">性别</div></th>
					</tr>
					<tr>
						<td><center><canvas id="terminal" height="250px"></canvas></center></td>
						<td><center><canvas id="gender" height="250px"></canvas></center></td>
					</tr>
				</table>
				
				<table border="1" height="49%" width="100%">
					<tr>
						<th align="center">年龄</th>
						<th align="center">ARPU值</th>
					</tr>
					<tr>
						<td><center><canvas id="age" height="250px"></canvas></center></td>
						<td><center><canvas id="arpu" height="250px"></canvas></center></td>
					</tr>
				</table>
			</div>
				<div id="showtable" style='margin-top:20px;'></div>
				<div id="page" class="page"></div>
		</div>
		<div class="bottom">
				Copyright &copy;<a href="http://scst.suda.edu.cn/" target='_blank'>苏州大学计算机科学与技术学院 机器学习数据挖掘实验室</a>
				&nbsp;<a href="mailto:ggxu.soochow@gmail.com">联系我们</a>
		</div>
	</div>	
</div>
	



</body>
<script src="js/transform.js"></script>
<script src="js/map.js"></script>
<script src="js/heatmap.js"></script>
<script src="js/event.js" charset="utf-8"></script>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="build/jquery.datetimepicker.full.js"></script>
<script src="js/bootstrap-switch.min.js"></script>
<script src="js/jquery.spinner.min.js"></script>
<script src="js/dvisual.js"></script>
<script>
(function ($) {  
  $('.spinner .btn:first-of-type').on('click', function() {  
    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);  
  });  
  $('.spinner .btn:last-of-type').on('click', function() {  
    $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);  
  });  
})(jQuery);  



$(".leftsidebar_box dt").css({"background-color":"#5E5E5E"});
$(".leftsidebar_box dt img").attr("src","images/left/select_xl01.png");
$(function(){
	$(".leftsidebar_box dd").hide();
	$(".leftsidebar_box dt").click(function(){
		$(".leftsidebar_box dt").css({"background-color":"#5E5E5E"});
		$(this).css({"background-color": "red"});
		$(this).parent().find('dd').removeClass("menu_chioce");
		$(".leftsidebar_box dt img").attr("src","images/left/select_xl01.png");
		$(this).parent().find('img').attr("src","images/left/select_xl.png");
		$(".menu_chioce").slideUp(); 
		$(this).parent().find('dd').slideToggle();
		$(this).parent().find('dd').addClass("menu_chioce");
	});
});

function draw_able()
{
	  map.setDefaultCursor("default");
	  map.on("mousedown", _onMousedown);
	  map.on("mousemove", _onMousemove);
      map.on("mouseup", _onMouseup);
      mouseTool.rectangle({
      strokeColor: "#FF33FF",
      strokeOpacity: 0.2,
      storkeWeight: 2,
      fillColor: "#1791fc",
      fillOpacity: 0.35,
      cursor: "url(http://webapi.amap.com/theme/v1.3/openhand.cur),default"
			 });
}

function draw_disable()
{
	map.setDefaultCursor("url(http://webapi.amap.com/theme/v1.3/openhand.cur),default");
	map.off("mousedown", _onMousedown);
    map.off("mousemove", _onMousemove);
    map.off("mouseup", _onMouseup);
    mouseTool.close();
}




$('#StartTime').datetimepicker({
	formatTime:'H:i',
	formatDate:'d.m.Y',
	defaultDate:'+03.01.1970', 
	defaultTime:'10:00',
	timepickerScrollbar:false
});

$('#EndTime').datetimepicker({
	formatTime:'H:i',
	formatDate:'d.m.Y',
	defaultDate:'+03.01.1970', 
	defaultTime:'10:00',
	timepickerScrollbar:false
});	

$("[name='my_checkbox']").bootstrapSwitch();

$('input[name="my_checkbox"]').on('switchChange.bootstrapSwitch', function(event, state) {
	  if(state===true)
	  {
	      draw_able();
	  }else
	  {
	     draw_disable();
	  }
	});
draw_able();
drawInit("terminal");
drawInit("gender");
drawInit("age");
drawInit("arpu");
</script>
</html>
