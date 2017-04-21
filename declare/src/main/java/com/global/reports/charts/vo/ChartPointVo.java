package com.global.reports.charts.vo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.global.framework.dbutils.support.Entity;

public class ChartPointVo extends Entity{
	private long x;
	private String xs;
	private int y;
	
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public String getXs() {
		return xs;
	}
	public void setXs(String xs) {
		try {
			this.x = 	new SimpleDateFormat("yyyy-MM-dd").parse(xs).getTime();
			} catch (ParseException e) {
				this.x =0;
			}
		this.xs = xs;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
