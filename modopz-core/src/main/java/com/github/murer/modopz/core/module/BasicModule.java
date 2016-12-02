package com.github.murer.modopz.core.module;

import com.github.murer.modopz.core.service.Echo;
import com.github.murer.modopz.core.service.Param;

public interface BasicModule extends Module {

	public Echo echo(@Param("echo") Echo echo);

	public String ping();

	public Object none();

	public void exp(@Param("code") Integer code);

}
