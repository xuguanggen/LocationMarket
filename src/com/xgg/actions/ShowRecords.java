package com.xgg.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;





import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xgg.beans.RecordBean;
import com.xgg.db.BasicMongo;
import com.xgg.utils.Tool;
public class ShowRecords extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session=request.getSession();


	public String getLefttoplng() {
		return lefttoplng;
	}
	public void setLefttoplng(String lefttoplng) {
		this.lefttoplng = lefttoplng;
	}
	public String getLefttoplat() {
		return lefttoplat;
	}
	public void setLefttoplat(String lefttoplat) {
		this.lefttoplat = lefttoplat;
	}
	public String getRightbottomlng() {
		return rightbottomlng;
	}
	public void setRightbottomlng(String rightbottomlng) {
		this.rightbottomlng = rightbottomlng;
	}
	public String getRightbottomlat() {
		return rightbottomlat;
	}
	public void setRightbottomlat(String rightbottomlat) {
		this.rightbottomlat = rightbottomlat;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getTopn() {
		return topn;
	}
	public void setTopn(String topn) {
		this.topn = topn;
	}
	private String lefttoplng;
	private String lefttoplat;
	private String rightbottomlng;
	private String rightbottomlat;
	private String StartTime;
	private String EndTime;
	private String topn;
	
	private String cur_page; 
	public String getCur_page() {
		return cur_page;
	}
	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}
	public void saveIntoExcel(ArrayList<RecordBean>alist) throws Exception
	{
		String path = session.getServletContext().getRealPath("/")+"csv"+"\\";
		path = path.replace("\\", "\\\\");
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(new Date());
		int year=calendar.get(Calendar.YEAR);
		String month= Tool.FormatValue(calendar.get(Calendar.MONTH)+1);
		String day=Tool.FormatValue(calendar.get(Calendar.DAY_OF_MONTH));
		String hour=Tool.FormatValue(calendar.get(Calendar.HOUR_OF_DAY));
		String minute=Tool.FormatValue(calendar.get(Calendar.MINUTE));
		String second=Tool.FormatValue(calendar.get(Calendar.SECOND));
		String fileName = year+month+day+"_"+hour+""+minute+""+second+".csv";
		session.setAttribute("fileName", fileName);
		File file = new File(path+fileName);
        FileWriter fw = null;
        BufferedWriter writer = null;
        fw = new FileWriter(file);
        writer = new BufferedWriter(fw);
        writer.write("ID,msisdn,lon,lat,hours,duration,occurrence");
        writer.newLine();
        for(int i=0;i<alist.size();i++){
          RecordBean rb = alist.get(i);
          writer.write(Integer.toString(i)+","+rb.toString());
          writer.newLine();//����
        }            
        writer.flush();	
        writer.close();
        fw.close();
	}
	
	private String table;
	
	private String pieString;
	
	public String getPieString() {
		return pieString;
	}
	public void setPieString(String pieString) {
		this.pieString = pieString;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	private String heatmap;
	
	public String getHeatmap() {
		return heatmap;
	}
	public void setHeatmap(String heatmap) {
		this.heatmap = heatmap;
	}
	
	private int pageCount;
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public String getHeatMapByaList(ArrayList<RecordBean> aList)
	{
		HashMap<String, Integer> hMap=new HashMap<String, Integer>();
		for(int i=0;i<aList.size();i++)
		{
			String lon=Double.toString(aList.get(i).getLon());
			String lat=Double.toString(aList.get(i).getLat());
			String key=lon+","+lat;
			if(hMap.containsKey(key))
			{
				int count=hMap.get(key);
				hMap.put(key, count+1);
			}else {
				hMap.put(key, 1);
			}			
		}
		heatmap="{point:[";
		for(String key:hMap.keySet())
		{
			int value=hMap.get(key);
			String lon= key.split(",")[0];
			String lat= key.split(",")[1];
			heatmap+="{'lng':"+lon+","+"'lat':"+lat+",'count':"+value+"},";
		}
		heatmap=heatmap.substring(0,heatmap.length()-1)+"]}";
		return heatmap;
	}
	
	
	// generate Duration Map
	public  HashMap<String, Integer> generateDurationMap(ArrayList<RecordBean> alist)
	{
		HashMap<String, Integer> hMap = new HashMap<String, Integer>();
		for(int i=0;i<alist.size();i++)
		{
			RecordBean recordBean=alist.get(i);
			String msisdn = recordBean.getMsisdn();
			int duration = recordBean.getDuration();
			if(hMap.containsKey(msisdn))
			{
				int cur_duration=hMap.get(msisdn);
				System.out.println("duration:"+msisdn+","+(cur_duration+duration));
				hMap.put(msisdn, cur_duration+duration);
			}else {
				hMap.put(msisdn, duration);
			}
		}
		return hMap;
	}
	
	// generate Occurrence Map
	public  HashMap<String, Integer> generateOccurrenceMap(ArrayList<RecordBean> alist)
	{
		HashMap<String, Integer> hMap = new HashMap<String, Integer>();
		for(int i=0;i<alist.size();i++)
		{
			RecordBean recordBean=alist.get(i);
			String msisdn = recordBean.getMsisdn();
			int occurrence = recordBean.getOccurance();
			if(hMap.containsKey(msisdn))
			{
				int cur_occurrence=hMap.get(msisdn);
				System.out.println("occurrence:"+msisdn+","+(cur_occurrence+occurrence));
				hMap.put(msisdn, cur_occurrence+occurrence);
			}else {
				hMap.put(msisdn, occurrence);
			}
		}
		return hMap;
	}
	
	
	public HashMap<String, String> mergeTwoHashMap(HashMap<String, Integer>durMap,HashMap<String, Integer>occMap)
	{
		HashMap<String, String> mergeMap = new HashMap<String, String>();
		for(String key:durMap.keySet())
		{
			int dura = durMap.get(key);
			int occ = occMap.get(key);
			String valueString = Integer.toString(dura)+","+Integer.toString(occ);
			mergeMap.put(key, valueString);
		}
		return mergeMap;
	}
	
	
	
	
	
	
	public String execute() throws Exception {
		double cur_lt_lng = Double.parseDouble(lefttoplng);
		double cur_lt_lat = Double.parseDouble(lefttoplat);
		double cur_rb_lng = Double.parseDouble(rightbottomlng);
		double cur_rb_lat = Double.parseDouble(rightbottomlat);
		int cur_startTime = Tool.getFormatTime(StartTime);
		int cur_endTime = Tool.getFormatTime(EndTime);
		int cur_topn = Integer.parseInt(topn);
		BasicMongo bMongo=new BasicMongo();
		ArrayList<RecordBean> aList =bMongo.query(cur_lt_lng, cur_rb_lat, cur_rb_lng, cur_lt_lat, cur_startTime, cur_endTime);
		BasicDBList msisdnList = bMongo.queryMsisdnList(cur_lt_lng, cur_rb_lat, cur_rb_lng, cur_lt_lat,cur_startTime, cur_endTime);
		pieString = bMongo.generatePieJsonFile(msisdnList, Tool.getMonth(StartTime), Tool.getMonth(EndTime));
		HashMap<String, Integer> duration_map = generateDurationMap(aList);		
		HashMap<String, Integer> occurrence_map = generateOccurrenceMap(aList);
		HashMap<String, String> mergeMap = mergeTwoHashMap(duration_map, occurrence_map);
		ArrayList<Map.Entry<String,String>> sort_mergeMap_list = Tool.sortMap(mergeMap,cur_topn);
		session.setAttribute("dur_occ", sort_mergeMap_list);
		//saveIntoExcel(aList);
		table="{table:[";
		int i=0;
		if(sort_mergeMap_list.size()<=Tool.page_per_count)
		{
			for(i=0;i<sort_mergeMap_list.size();i++)
			{
				String msisdn=sort_mergeMap_list.get(i).getKey();
				String[] valueString = sort_mergeMap_list.get(i).getValue().trim().split(",");
				table+="{'id':"+i+",'msisdn':"+msisdn
						+ ",'duration':"+valueString[0]+",'occurrence':"+valueString[1]+"},";
			}
		}else {
			for(i=0;i<Tool.page_per_count;i++)
			{
				String msisdn=sort_mergeMap_list.get(i).getKey();
				String[] valueString = sort_mergeMap_list.get(i).getValue().trim().split(",");
				table+="{'id':"+i+",'msisdn':"+msisdn
						+ ",'duration':"+valueString[0]+",'occurrence':"+valueString[1]+"},";
			}
		}		
		table=table.substring(0,table.length()-1)+"]}";
		heatmap=getHeatMapByaList(aList);
		pageCount=getPageCount(sort_mergeMap_list);
		return SUCCESS;
	}

	
	public int getPageCount(ArrayList<Map.Entry<String,String>> aList)
	{
		if(aList.size()%Tool.page_per_count==0)
		{
			pageCount=aList.size()/Tool.page_per_count;
		}else {
			pageCount=aList.size()/Tool.page_per_count+1;
		}
		return pageCount;
	}

	public String splitPage() {
		int cur_page_value = Integer.parseInt(cur_page.trim());
		ArrayList<Map.Entry<String,String>> sort_mergeMap_list = (ArrayList<Map.Entry<String,String>>)session.getAttribute("dur_occ");
		pageCount=getPageCount(sort_mergeMap_list);
		table="{table:[";
		int end=0;		
		if(cur_page_value==pageCount)
		{
			end=sort_mergeMap_list.size();
		}else {
			end=cur_page_value*Tool.page_per_count;
		}
		for(int i=(cur_page_value-1)*Tool.page_per_count;i<end;i++)
		{
			String msisdn=sort_mergeMap_list.get(i).getKey();
			String[] valueString = sort_mergeMap_list.get(i).getValue().trim().split(",");
			table+="{'id':"+i+",'msisdn':"+msisdn
					+ ",'duration':"+valueString[0]+",'occurrence':"+valueString[1]+"},";
		}
		table=table.substring(0,table.length()-1)+"]}";
		return SUCCESS;
	}
	
	
	
	public BasicDBList getMsisdnList(ArrayList<RecordBean> alist)
	{
		BasicDBList msisdnList = new BasicDBList();
		for(int i=0;i<alist.size();i++)
		{
			msisdnList.add(Long.parseLong(alist.get(i).getMsisdn()));
		}
		return msisdnList;
	}
	
	
	

}
