package com.hxl.miracle.myhttpapplication;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 网络请求统计
 * @author wangpengcheng
 * 
 */
public class HttpReqInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reqUrl;// 请求链接带参数
	private Timestamp reqBegTime;// 请求开始时间
	private Timestamp reqEndTime;// 请求响应时间
	private int errorCode;// 返回错误码
	private String errorMsg;// 返回错误信息
	public HttpReqInfo(String reqUrl, Timestamp reqBegTime,
					   Timestamp reqEndTime, int errorCode, String errorMsg) {
		super();
		this.reqUrl = reqUrl;
		this.reqBegTime = reqBegTime;
		this.reqEndTime = reqEndTime;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public Timestamp getReqBegTime() {
		return reqBegTime;
	}

	public void setReqBegTime(Timestamp reqBegTime) {
		this.reqBegTime = reqBegTime;
	}

	public Timestamp getReqEndTime() {
		return reqEndTime;
	}

	public void setReqEndTime(Timestamp reqEndTime) {
		this.reqEndTime = reqEndTime;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "HttpReqInfo [reqUrl=" + reqUrl + ", reqBegTime=" + reqBegTime
				+ ", reqEndTime=" + reqEndTime + ", errorCode=" + errorCode
				+ ", errorMsg=" + errorMsg + "]";
	}

}
