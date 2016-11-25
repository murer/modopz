package com.murerz.modopz.core.service;

public class Resp<A> {

	private Integer code = 200;

	private Class<A> type;

	private A result;

	public Class<A> getType() {
		return type;
	}

	public Resp<A> setType(Class<A> type) {
		this.type = type;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public Resp<A> setCode(Integer code) {
		this.code = code;
		return this;
	}

	public A getResult() {
		return result;
	}

	public Resp<A> setResult(A result) {
		this.result = result;
		return this;
	}

	public static <T> Resp<T> create(Class<T> type) {
		return new Resp<T>().setType(type);
	}

	@Override
	public String toString() {
		return "[Resp code=" + code + ", type=" + type + "]";
	}

	public String type() {
		return type == null ? null : type.getName();
	}

}
