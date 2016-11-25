package com.murerz.modopz.core.service;

public interface BasicModule extends Module {

	public Echo echo(@Param("echo") Echo echo);

	public String ping();

}
