package com.murerz.modopz.core.module;

import com.murerz.modopz.core.service.Echo;
import com.murerz.modopz.core.service.Param;

public interface BasicModule extends Module {

	public Echo echo(@Param("echo") Echo echo);

	public String ping();

	public Object none();

}
