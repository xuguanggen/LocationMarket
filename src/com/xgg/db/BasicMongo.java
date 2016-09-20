package com.xgg.db;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.xgg.beans.RecordBean;
import com.xgg.utils.Tool;

public class BasicMongo {
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session=request.getSession();

	public DB getDB(String dbname)
	{
		DB monDb= null;
		try {
			MongoClient mongoClient=new MongoClient("localhost",27017);
			monDb = mongoClient.getDB(dbname);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return monDb;
	}

	public DBCollection getTable(String dbname,String tablename)
	{
		DB db=getDB(dbname);
		DBCollection dbCollection=db.getCollection(tablename);
		return dbCollection;
	}
	
	public double getLon(Object o)
	{
		return Double.parseDouble(o.toString().split(",")[0].toString().split(":")[1].toString());
	}
	public double getLat(Object o)
	{
		String lat =o.toString().split(",")[1].split(":")[1].toString();
		return Double.parseDouble(lat.substring(0,lat.length()-1));
	}
	
	public BasicDBList queryMsisdnList(double minlon,double minlat,double maxlon,double maxlat,
			int startTime,int endTime)
	{
		BasicDBList bDbList = new BasicDBList();
		DBCollection table = getTable("user_discover", "user_basic");
		DBObject dbObject=new BasicDBObject();
		dbObject.put("loc.lon", new BasicDBObject("$gte",minlon).append("$lte", maxlon));
		dbObject.put("loc.lat", new BasicDBObject("$gte",minlat).append("$lte", maxlat));
		dbObject.put("hour",new BasicDBObject("$gte",startTime).append("$lte", endTime));
		DBCursor dbCursor = table.find(dbObject).limit(Tool.TOPN);
		while(dbCursor.hasNext())
		{
			DBObject object=dbCursor.next();
			String msisdn = object.get("msisdn").toString();
			bDbList.add(Long.parseLong(msisdn));
		}
		return bDbList;
	}
	public ArrayList<RecordBean> query(double minlon,double minlat,double maxlon,double maxlat,
			int startTime,int endTime)
	{
		
		System.out.println("************************");
		System.out.println("minlon:"+minlon);
		System.out.println("minlat:"+minlat);
		System.out.println("maxlon:"+maxlon);
		System.out.println("maxlat:"+maxlat);
		System.out.println(startTime+"---->"+endTime);
		System.out.println("############################");
		DBCollection table = getTable("user_discover", "user_basic");
		DBObject dbObject=new BasicDBObject();
		dbObject.put("loc.lon", new BasicDBObject("$gte",minlon).append("$lte", maxlon));
		dbObject.put("loc.lat", new BasicDBObject("$gte",minlat).append("$lte", maxlat));
		dbObject.put("hour",new BasicDBObject("$gte",startTime).append("$lte", endTime));
		DBCursor dbCursor = table.find(dbObject).sort(new BasicDBObject("duration",-1));
		
		ArrayList<RecordBean> aDbObjects =new ArrayList<RecordBean>();
		while(dbCursor.hasNext())
		{
			DBObject object=dbCursor.next();
			String msisdn = object.get("msisdn").toString();
			double lon= getLon(object.get("loc"));
			double lat= getLat(object.get("loc"));
			int hour=Integer.parseInt(object.get("hour").toString());
			int duration=Integer.parseInt(object.get("duration").toString());
			int occurrence=Integer.parseInt(object.get("occurrence").toString());
			RecordBean rBean=new RecordBean(msisdn, lon, lat, hour, duration, occurrence);
			aDbObjects.add(rBean);
		}
		return aDbObjects;
	}
	
	public HashMap<String, Integer> addElements(String key,HashMap<String, Integer> hMap)
	{
		if(hMap.containsKey(key))
		{
			int count = hMap.get(key);
			hMap.put(key, count+1);
		}else {
			hMap.put(key, 1);
		}
		return hMap;
	}
	
	public HashMap<String, HashMap<String, Integer>> Init_Pie()
	{
		HashMap<String, HashMap<String, Integer>> hMap = new HashMap<String, HashMap<String, Integer>>();
		hMap.put("terminal", new HashMap<String, Integer>());
		hMap.put("gender", new HashMap<String, Integer>());
		hMap.put("age", new HashMap<String, Integer>());
		hMap.put("arpu", new HashMap<String, Integer>());
		DBCollection user_attr_collection = getTable("user_discover", "user_attributes");
		DBCursor dbCursor = user_attr_collection.find();
		while(dbCursor.hasNext())
		{
			DBObject object = dbCursor.next();
			String msisdn = object.get("msisdn").toString();
			String gender = object.get("gender").toString();
			String age = object.get("age").toString();
			String terminal = object.get("hq_dm_brand").toString();
			String arpu = object.get("arpu").toString();
			
			HashMap<String, Integer> genderMap = hMap.get("gender");
			if("1".equals(gender)||"0".equals(gender))
				genderMap = addElements(gender, genderMap);
			
			HashMap<String, Integer> terminalMap = hMap.get("terminal");
			terminalMap = addElements(terminal, terminalMap);
					
			
			if(!"NULL".equals(age))
			{
				int value_age = Integer.parseInt(age);
				HashMap<String, Integer> ageMap = hMap.get("age");
				if(value_age>=15 && value_age<=30)
				{
					ageMap = addElements("15-30", ageMap);
				}else if(value_age>=31 && value_age<=45)
				{
					ageMap = addElements("31-45", ageMap);
				}else if(value_age>=46 && value_age<=60)
				{
					ageMap = addElements("46-60", ageMap);
				}else if(value_age>=61){
					ageMap = addElements(">=61", ageMap);
				}
			}
			
			if(!"NULL".equals(arpu))
			{
				HashMap<String, Integer> arpuMap = hMap.get("arpu");
				Double value_arpu = Double.parseDouble(arpu);
				if(value_arpu>=0 && value_arpu<=10)
				{
					arpuMap = addElements("0-10", arpuMap);
				}else if(value_arpu>10 && value_arpu<=30)
				{
					arpuMap = addElements("10-30", arpuMap);
				}else if(value_arpu>30 && value_arpu<=50)
				{
					arpuMap = addElements("30-50", arpuMap);
				}else if(value_arpu>50 && value_arpu<=100)
				{
					arpuMap = addElements("50-100", arpuMap);
				}else if(value_arpu>100 && value_arpu<=200)
				{
					arpuMap = addElements("100-200", arpuMap);
				}else if(value_arpu>200 && value_arpu<=500)
				{
					arpuMap = addElements("200-500", arpuMap);
				}else if(value_arpu>500)
				{
					arpuMap = addElements(">500", arpuMap);
				}
			}
			
		}
		HashMap<String, Integer> mergeTerminal_Map = hMap.get("terminal");
		ArrayList<String> removeKeysArrayList =new ArrayList<String>();
		int other_count = 0;
		for(String key:mergeTerminal_Map.keySet())
		{
			int terminal_count = mergeTerminal_Map.get(key);
			if(terminal_count < Tool.TERMINAL_COUNT)
			{
				other_count += terminal_count;
				removeKeysArrayList.add(key);
			}
		}
		mergeTerminal_Map.put("其它", other_count);
		for(int i=0;i<removeKeysArrayList.size();i++)
		{
			mergeTerminal_Map.remove(removeKeysArrayList.get(i));
		}
		hMap.put("terminal", mergeTerminal_Map);
		return hMap;
	}
	
	
	public String generatePieJsonFile(BasicDBList msisdnList,int StartTime,int EndTime) throws Exception
	{
		//====================================================
		HashMap<String, HashMap<String, Integer>> hMap = new HashMap<String, HashMap<String, Integer>>();
		hMap.put("terminal", new HashMap<String, Integer>());
		hMap.put("gender", new HashMap<String, Integer>());
		hMap.put("age", new HashMap<String, Integer>());
		hMap.put("arpu", new HashMap<String, Integer>());
		//====================================================
		DBCollection table = getTable("user_discover", "user_attributes");
		DBObject dbObject=new BasicDBObject();
		dbObject.put("msisdn", new BasicDBObject("$in", msisdnList));
		dbObject.put("month_partition",new BasicDBObject("$gte",StartTime).append("$lte", EndTime));
		DBCursor dbCursor = table.find(dbObject);
		while(dbCursor.hasNext())
		{
			DBObject object = dbCursor.next();
			
			String msisdn = object.get("msisdn").toString();
			String gender = object.get("gender").toString();
			String age = object.get("age").toString();
			String terminal = object.get("hq_dm_brand").toString();
			String arpu = object.get("arpu").toString();
			HashMap<String, Integer> genderMap = hMap.get("gender");
			if("1".equals(gender)||"0".equals(gender))
				genderMap = addElements(gender, genderMap);
			
			HashMap<String, Integer> terminalMap = hMap.get("terminal");
			terminalMap = addElements(terminal, terminalMap);
			
			if(!"NULL".equals(age))
			{
				int value_age = Integer.parseInt(age);
				HashMap<String, Integer> ageMap = hMap.get("age");
				if(value_age>=15 && value_age<=30)
				{
					ageMap = addElements("15-30", ageMap);
				}else if(value_age>=31 && value_age<=45)
				{
					ageMap = addElements("31-45", ageMap);
				}else if(value_age>=46 && value_age<=60)
				{
					ageMap = addElements("46-60", ageMap);
				}else if(value_age>=61){
					ageMap = addElements(">=61", ageMap);
				}
			}
			
			if(!"NULL".equals(arpu))
			{
				HashMap<String, Integer> arpuMap = hMap.get("arpu");
				Double value_arpu = Double.parseDouble(arpu);
				if(value_arpu>=0 && value_arpu<=10)
				{
					arpuMap = addElements("0-10", arpuMap);
				}else if(value_arpu>10 && value_arpu<=30)
				{
					arpuMap = addElements("10-30", arpuMap);
				}else if(value_arpu>30 && value_arpu<=50)
				{
					arpuMap = addElements("30-50", arpuMap);
				}else if(value_arpu>50 && value_arpu<=100)
				{
					arpuMap = addElements("50-100", arpuMap);
				}else if(value_arpu>100)
				{
					arpuMap = addElements(">100", arpuMap);
				}
			}
		}
		
		HashMap<String, Integer> mergeTerminal_Map = hMap.get("terminal");
		ArrayList<String> removeKeysArrayList =new ArrayList<String>();
		int other_count = 0;
		for(String key:mergeTerminal_Map.keySet())
		{
			int terminal_count = mergeTerminal_Map.get(key);
			if(terminal_count < Tool.TERMINAL_COUNT)
			{
				other_count += terminal_count;
				removeKeysArrayList.add(key);
			}
		}
		mergeTerminal_Map.put("其它", other_count);
		for(int i=0;i<removeKeysArrayList.size();i++)
		{
			mergeTerminal_Map.remove(removeKeysArrayList.get(i));
		}
		hMap.put("terminal", mergeTerminal_Map);
		String jsonString = "{";
		for(String key:hMap.keySet())
		{
			jsonString += "'"+key+"':[";
			HashMap<String, Integer> subMap = hMap.get(key);
			for(String subKey:subMap.keySet())
			{
				String tempString = "{'name':'"+subKey+"','nums':"+subMap.get(subKey)+"},";
				jsonString += tempString;
			}
			jsonString  = jsonString.substring(0, jsonString.length()-1) + "],";
		}
		jsonString = jsonString.substring(0,jsonString.length()-1)+"}";
		return jsonString;
	}
}
