package com.haven.hckp.bean;

/**
 * 实体类
 */
public abstract class Entity extends Base {

	protected int id;

	public int getId() {
		return id;
	}

	protected String cacheKey;

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
