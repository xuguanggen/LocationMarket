
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
    <link rel="stylesheet" type="text/css" href="css/jquery.datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>


    <div class="part1" style="">
        <fieldset style="position: relative; float: left; width: 50%">
            <legend>地图</legend>
            <div id="container" style="position: relative; height: 500px"></div>
            <div class="button-group" style="bottom: 10px">
                <input id="bt1" type="button" class="button" value="开启鼠标框选矩形">
                <input id="bt2" type="button" class="button" value="关闭鼠标框选矩形">
                </div>
        </fieldset>
        <fieldset style="position: relative">
            <legend>热力图</legend>
            <div id="heatmap" style="position: relative; height: 500px"></div>
        </fieldset>
    </div>
    <div class="part2">
        <fieldset>
            <legend>空间范围</legend>
            左上角:
            <input id="lefttoplng" type="text" name="lefttoplng" value="121.458524" placeholder="经度">
            <input id="lefttoplat" type="text" name="lefttoplat" value="31.235313" placeholder="纬度">
            <br />
            右下角:
            <input id="rightbottomlng" type="text" name="rightbottomlng" value="121.478524" placeholder="经度">
            <input id="rightbottomlat" type="text" name="rightbottomlat" value="31.215313" placeholder="纬度">
        </fieldset>
    </div>
    <input id="search" type="button" class="button" value="开始查询">

 
    <form id="testform" name="testform">
    	lon：<input type="text" name="lon" id="lon"/> 
		点击查询:<input type="button" value="查询" onClick="pass_value()" />    
    </form>
</body>
<script src="js/map.js"></script>
<script src="js/transform.js"></script>
<script type="text/javascript" src="http://www.pengyaou.com/jquery-1.4.min.js"></script>
<script src="js/jquery.js"></script>
<script src="js/event.js"></script>
<script src="build/jquery.datetimepicker.full.js"></script>
<script src="js/heatmap.js"></script>
<script type="text/javascript">
	function pass_value()
	{
	   var arr=$('#testform').serialize();
	   $.ajax({
		   url:'testaction',
		   data:arr,
		   type:"post",
		   dataType:"json",
		   success:function(data)
		   {
		   	   alert(data.heatmap);
			   var hd = eval("(" + data.heatmap + ")");
			   //alert(hd.point);			   
			   /*for( var i=0;i<hd.point.length;i++)
			   {
			     alert(hd.point[i]["lng"]);
			   }*/
			   alert(hd.point.length);
			   drawHeatmap(hd.point);			   
		   }
	   })
	}
</script>
</html>
