package com.xgg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Tool {
	public static int getFormatTime(String time)
	{
		String cur_time=time.substring(0,4)+time.substring(5, 7)+time.substring(8,10)+time.substring(11,13);
		return Integer.parseInt(cur_time);
	}
	
	public static int getMonth(String time)
	{
		String cur_time=time.substring(0,4)+time.substring(5, 7);
		return Integer.parseInt(cur_time);
	}
	
	public static int TERMINAL_COUNT=50;
	public static int TOPN=2000;
	public static String FormatValue(int v)
	{
		if(v>=10)
			return Integer.toString(v);
		else {
			return "0"+Integer.toString(v);
		}
	}
	
	public static int page_per_count=50;


	
//	public static ArrayList<Map.Entry<String,Integer>> sortMap(Map map){
//	     List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
//	     Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
//	         public int compare(Map.Entry<String, Integer> obj1 , Map.Entry<String, Integer> obj2) {
//	             return obj2.getValue() - obj1.getValue();
//	         }
//	     });
//	      return (ArrayList<Entry<String, Integer>>) entries;
//	}

	public static int myCompare(String str1,String str2)
	{
		String []array_1=str1.trim().split(",");
		String []array_2=str2.trim().split(",");
		int duration_1=Integer.parseInt(array_1[0]);
		int occurrence_1=Integer.parseInt(array_1[1]);
		int duration_2=Integer.parseInt(array_2[0]);
		int occurrence_2=Integer.parseInt(array_2[1]);
		if(duration_1>duration_2)
		{
			return 1;
		}else if(duration_1==duration_2)
		{
			if(occurrence_1==occurrence_2)
			{
				return 0;
			}
			else if(occurrence_1>occurrence_2)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}else {
			return -1;
		}
	}
	
	
	public static ArrayList<Map.Entry<String,String>> sortMap(Map map,int topn){
	     List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>(map.entrySet());
	     Collections.sort(entries, new Comparator<Map.Entry<String, String>>() {
	         public int compare(Map.Entry<String, String> obj1 , Map.Entry<String, String> obj2) {
	             return myCompare(obj2.getValue(), obj1.getValue()) ;
	         }
	     });
	     ArrayList<Entry<String, String>> returnEntries = new ArrayList<Map.Entry<String,String>>();
	     for(int i=0;i<topn;i++)
	     {
	    	 returnEntries.add(entries.get(i));
	     }
	     return returnEntries;
	}
	
	
}
