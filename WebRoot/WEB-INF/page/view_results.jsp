<%@page import="com.xgg.beans.RecordBean"%>
<%@page import="com.mongodb.DBObject"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>时空查询结果展示</title>
    <link rel="shortcut icon" href="images/favicon.ico"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>

  <body>
   <%
   		ArrayList<RecordBean> alist =(ArrayList<RecordBean>)(session.getAttribute("result"));
   		String fileName=session.getAttribute("fileName").toString();
   		System.out.println(fileName);
   		out.println("Top:"+alist.size()+"&nbsp;&nbsp;&nbsp;<a href='http://localhost:8080/LocationMarket/csv/"+fileName+"' download='"+fileName+".csv'><input type='button' value='导出'/></a>");
   		out.println("<table id='search_result' cellspacing=0 border=1 bordercolor=green>");
   		out.println("<tr><td>ID</td><td>msisdn</td><td>lon</td><td>lat</td><td>hours</td><td>duration</td><td>occurance</td></tr>");
   		for(int i=0;i<alist.size();i++)
   		{
   			RecordBean recordBean=alist.get(i);
   			if(i%2==0)
   			{
   				out.println("<tr style='background-color:#8DEEEE'>");
   			}else
   				out.println("<tr>");
   			out.println("<td>"+i+"</td><td height=10px align='center'>" + recordBean.getMsisdn()
															+ "</td><td align='center'>" +recordBean.getLon()
															+ "</td><td align='center'>" + recordBean.getLat()
															+ "</td><td align='center'>" + recordBean.getHours()
															+ "</td><td align='center'>" + recordBean.getDuration()
															+ "</td><td align='center'>" + recordBean.getOccurance()													
															+ "</td></tr>");
   		}      
   		out.println("</table>");
    %>  
   
    
  </body>
</html>
