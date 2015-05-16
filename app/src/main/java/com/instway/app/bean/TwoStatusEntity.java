package com.instway.app.bean;
/**
 * 二级级状态实体类
 * @author 三人行技术开发团队
 */
public class TwoStatusEntity {
	/* 状态名称 */
	private String statusName;
	/* 预计完成时间 */
	private String completeTime;
	/* 是否已完成 */
	private boolean isfinished; //0为未完成，1为已完成
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public boolean isIsfinished() {
		return isfinished;
	}
	public void setIsfinished(boolean isfinished) {
		this.isfinished = isfinished;
	}
	
	
	
}
