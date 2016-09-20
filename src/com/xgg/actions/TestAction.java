package com.xgg.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.beans.RecordBean;
import com.xgg.db.BasicMongo;

public class TestAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	HttpSession session=request.getSession();
	
	
	private String heatmap;
	public String getHeatmap() {
		return heatmap;
	}
	public void setHeatmap(String heatmap) {
		this.heatmap = heatmap;
	}
	
	private String xxx;
	
	public String getXxx() {
		return xxx;
	}
	public void setXxx(String xxx) {
		this.xxx = xxx;
	}
	@Override
	public String execute() throws Exception {
		BasicMongo bMongo=new BasicMongo();
		ArrayList<RecordBean>aList =bMongo.query(121.403103, 31.197946, 121.523589, 31.258133, 2016070110, 2016070210);
		heatmap="{point:[";
		for(int i=0;i<aList.size();i++)
		{
			heatmap+="{'lng':"+aList.get(i).getLon()+","+"'lat':"+aList.get(i).getLat()+",'count':1},";
		}
		heatmap=heatmap.substring(0,heatmap.length()-1)+"]}";
		xxx="fuckunicom";
		System.out.println(heatmap);
		return SUCCESS;
	}
	
	

}
