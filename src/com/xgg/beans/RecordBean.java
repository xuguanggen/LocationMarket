package com.xgg.beans;

public class RecordBean {
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return msisdn+","+Double.toString(lon)+","+Double.toString(lat)+","+
				Integer.toString(hours)+","+Integer.toString(duration)+","+Integer.toString(occurance);
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getOccurance() {
		return occurance;
	}
	public void setOccurance(int occurance) {
		this.occurance = occurance;
	}
	private String msisdn;
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
	private double lon;
	private double lat;
	private int hours;
	private int duration;
	private int occurance;
	public RecordBean(String msisdn,double lon,double lat,int hours,int duration,int occurance )
	{
		this.msisdn=msisdn;
		this.lon=lon;
		this.lat=lat;
		this.hours=hours;
		this.duration=duration;
		this.occurance=occurance;
	}
	public RecordBean()
	{}
}
