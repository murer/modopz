package com.murerz.modopz.core.kernel;

import java.util.ArrayList;
import java.util.List;

public class MOEchoModule extends MOModule {

	@Override
	public List<Class<?>> getCommands() {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		ret.add(MOEchoCommand.class);
		return ret;
	}

}
