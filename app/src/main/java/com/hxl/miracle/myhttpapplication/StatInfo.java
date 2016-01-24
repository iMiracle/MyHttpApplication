package com.hxl.miracle.myhttpapplication;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StatInfo {
	//访问页面序号
	private int pageSerial = 0;
	//广告展示序号
	private int vtAdvertSerial = 0;
	//访问开始时间
	private Timestamp startTime;
	//访问结束时间
	private Timestamp lastTime;

	//网络请求信息
	private List<HttpReqInfo> httpReqList = new ArrayList<HttpReqInfo>();

	public int getPageSerial() {
		return pageSerial;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}


	public List<HttpReqInfo> getHttpReqList() {
		return httpReqList;
	}

	public void addHttpReqInfo(HttpReqInfo httpReqInfo) {
		this.httpReqList.add(httpReqInfo);
	}

	/**
	 * 一次写入日志文件后清空
	 */
	public void clearList(){
		httpReqList.clear();
	}
}
