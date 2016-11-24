package com.murerz.modopz.core.service;

public class AResp<A> {

	private Integer code = 200;

	private A result;

	public Integer getCode() {
		return code;
	}

	public AResp<A> setCode(Integer code) {
		this.code = code;
		return this;
	}

	public A getResult() {
		return result;
	}

	public AResp<A> setResult(A result) {
		this.result = result;
		return this;
	}

	public static <T> AResp<T> create(T result) {
		return new AResp<T>().setResult(result);
	}

}
