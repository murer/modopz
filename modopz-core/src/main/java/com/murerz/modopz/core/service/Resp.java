package com.murerz.modopz.core.service;

public class Resp<A> {

	private Integer code = 200;

	private A result;

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

	public static <T> Resp<T> create(T result) {
		return new Resp<T>().setResult(result);
	}

	@Override
	public String toString() {
		return "[Resp code=" + code + ", type=" + type() + "]";
	}

	public String type() {
		return result == null ? null : result.getClass().getName();
	}

}
