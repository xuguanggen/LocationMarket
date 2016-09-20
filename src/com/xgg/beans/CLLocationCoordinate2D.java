package com.xgg.beans;

import java.util.ArrayList;

public class CLLocationCoordinate2D {
	public double a= 6378245.0;
	public double ee=0.00669342162296594323;
	private double lon;
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	private double lat;
	public CLLocationCoordinate2D(double lon,double lat)
	{
		this.lon=lon;
		this.lat=lat;
	}
	public boolean outofChina(CLLocationCoordinate2D clLocationCoordinate2D)
	{
		if(clLocationCoordinate2D.getLon()<72.004 || clLocationCoordinate2D.getLon()>137.8347)
			return true;
		if(clLocationCoordinate2D.getLat()<0.8293 || clLocationCoordinate2D.getLat()>55.8271)
			return true;
		return false;
	}
	
	public double transformLat(double x, double y)
	{
	    double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
	    ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
	    ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
	    ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y *Math.PI / 30.0)) * 2.0 / 3.0;
	    return ret;
	}

	public double transformLon(double x, double y)
	{
	    double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
	    ret += (20.0 * Math.sin(6.0 * x *Math.PI) + 20.0 * Math.sin(2.0 * x *Math.PI)) * 2.0 / 3.0;
	    ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
	    ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
	    return ret;
	}
	
	
	//地球->高德
	public CLLocationCoordinate2D wgs2gcj()
	{
		double wlon=this.getLon();
		double wlat=this.getLat();
		double dlon=transformLon(wlon-105.0, wlat-35.0);
		double dlat=transformLat(wlon-105.0, wlat-35.0);
		double radLat=wlat/180.0*Math.PI;
		double magic=Math.sin(radLat);
		
		magic=1-ee*magic*magic;
		double sqrtMagic=Math.sqrt(magic);
		dlat=(dlat*180.0)/((a*(1-ee))/(magic*sqrtMagic)*Math.PI);
		dlon=(dlon*180.0)/(a/sqrtMagic*Math.cos(radLat)*Math.PI);
		System.out.println("地球->火星");
		System.out.println((wlon+dlon)+","+(wlat+dlat));
		System.out.println("===============\n");
		return new CLLocationCoordinate2D(wlon+dlon, wlat+dlat);
	}
	
	
}
