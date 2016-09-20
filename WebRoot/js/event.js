function getObject(id)
{
   return document.getElementById(id);
}


function fillTable(tablestr)
{
	var strhtml="<table class='table table-striped table-bordered table-hover table-condensed'><tr>" +
		"<th><center><b>序号</b></center></th>"+"<th><center><b>手机号码</b></center></th>"+"<th><center><b>驻留时间</b></center></th>"+"<th><center><b>出现次数</b></center></th><tr>";
	var tableJson = eval("(" + tablestr + ")");
	for (var i=0;i<tableJson.table.length;i++)
	{
	  strhtml=strhtml+"<tr>"+
	  "<td><center>"+tableJson.table[i]["id"]+"</center></td>"+
	  "<td><center>"+tableJson.table[i]["msisdn"]+"</center></td>"+
	  "<td><center>"+tableJson.table[i]["duration"]+"</center></td>"+
	  "<td><center>"+tableJson.table[i]["occurrence"]+"</center></td>"+
	  "</tr>";
	}
	strhtml=strhtml+"</table>";
	getObject("showtable").innerHTML=strhtml;
}

function splitClick(value)
{
	$.ajax({
		   url:'ShowRecords!splitPage',
		   data:{cur_page:value},
		   type:"post",
		   dataType:"json",
		   success:function(data)
		   {
			  fillTable(data.table);
		   }
	   });
}
function fillPage(pageCount)
{
	var strhtml="<nav><ul class='pagination'>";
	for(var i=1;i<=pageCount;i++)
	{
		strhtml+="<li><input type='button' value='"+i+"' text='"+i+"' onClick='splitClick(this.value)'/></li>";
	}
	strhtml+="</ul></nav>";
	getObject("page").innerHTML=strhtml;
}

function drawPie(pieType,labelList,valueList,textList)
{
	s = new DVisual(pieType);
	s.addElement(new DVPieChart({
		'X':labelList,
		'Y':valueList,
		'text':textList,
		'style':['showtext']}));
	s.draw();
}

function  drawPie_callBack(pie)
{
	for(var pieType in pie)
	{
		var labelList =new Array();
		var valueList =new Array();
		var textList =new Array();
		var sum = 0;
		for(var i=0;i<pie[pieType].length;i++)
		{
			sum = sum + (parseInt(pie[pieType][i]["nums"]));
		}
		for(var i = 0;i<pie[pieType].length;i++)
		{
			labelList[i]=(pie[pieType][i]["name"]);
			var value =(parseInt(pie[pieType][i]["nums"]))*1.0/sum;
			
			valueList[i] = parseFloat(value.toFixed(2));
			textList[i] = labelList[i]+": "+(valueList[i]*100).toString().substr(0, 3)+"%";
		}
		drawPie(pieType,labelList,valueList,textList);
	}
}


function submitToBackEnd()
{
   var arr=$('#searchform').serialize();
   $.ajax({
	   url:'ShowRecords',
	   data:arr,
	   type:"post",
	   dataType:"json",
	   success:function(data)
	   {		 
		  var hd = eval("(" + data.heatmap + ")");
		  drawHeatmap(hd.point);
		  heatmap.setZoom(map.getZoom());		  
		  var pie = eval("(" + data.pieString + ")");
		  drawPie_callBack(pie);	
		  var chart_div = getObject("showPart");
          chart_div.style.display="block";
		  fillTable(data.table);
		  fillPage(data.pageCount);
		  
	   }
   });
}

function clearInput()
{
	getObject("lefttoplng").value ="";
	getObject("lefttoplat").value ="";
	getObject("rightbottomlng").value ="";
	getObject("rightbottomlat").value ="";	
	getObject("StartTime").value ="";
	getObject("EndTime").value ="";
	map.clearMap();
}


function drawInit(pieType)
{
	var labelList =new Array();
	var valueList =new Array();
	var dataroot = "json/config.json";
	$.getJSON(dataroot,function(data)
	{
		s = new DVisual(pieType);
		for(var i =0;i<data[pieType].length;i++)
		{
			labelList[i]=(data[pieType][i]["name"]);
			valueList[i]=(parseInt(data[pieType][i]["nums"]));
		}
		s.addElement(new DVPieChart({
			'X':labelList,
			'Y':valueList,
			'style':['showtext']}));
		s.draw();
	});		
}

