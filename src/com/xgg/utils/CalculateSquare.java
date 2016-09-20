package com.xgg.utils;

import java.util.ArrayList;

public class CalculateSquare {
	public ArrayList<Double> calculatePoint(double lon,double lat,int r)
	{
		ArrayList<Double> square = new ArrayList<Double>();
		double anlge_change = 180*r/(Math.PI*6378137);
		double LeftBottom_Lon = lon-anlge_change;
		double LeftBottom_Lat = lat-anlge_change;
		double RightTop_Lon = lon+anlge_change;
		double RightTop_Lat = lat+anlge_change;
		square.add(LeftBottom_Lon);
		square.add(LeftBottom_Lat);
		square.add(RightTop_Lon);
		square.add(RightTop_Lat);
		return square;
	}
}
