package com.xgg.db;


import java.sql.*;
import java.util.ArrayList;

import com.xgg.beans.RecordBean;

public class BasicDB {
	private Connection cn=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	private String sql;
	
	public void OpenDB()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/unicom";
			String user = "root";
			String psd = "12345";
			cn = DriverManager.getConnection(url, user, psd);
			System.out.println("===== connect Success ==========");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void CloseDB()
	{
		if(rs!=null)
		{
			try{
				rs.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try{
				ps.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(cn!=null)
		{
			try
			{
				cn.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<RecordBean> queryDB(String sql,String[]paras)
	{
		ArrayList<RecordBean> aList = new ArrayList<RecordBean>();
		this.OpenDB();
		try {
			ps = cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			ResultSetMetaData rsmdData =rs.getMetaData();
			int cols=rsmdData.getColumnCount();
			while(rs.next())
			{
				Object[]objects = new Object[cols];
				RecordBean rBean =new RecordBean();
				for(int i=0;i<objects.length;i++)
				{
					objects[i]=rs.getObject(i+1).toString();
				}
				rBean=new RecordBean(objects[0].toString(),Double.parseDouble(objects[1].toString()),Double.parseDouble(objects[2].toString()),
						Integer.parseInt(objects[3].toString()),Integer.parseInt(objects[4].toString()),Integer.parseInt(objects[5].toString()));
				aList.add(rBean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			this.CloseDB();
		}
		return aList;
	}
}
