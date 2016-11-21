package com.murerz.dsopz.core;

import com.murerz.dsopz.core.DOZJdkLogFactory.DOZJdkLog;

public class JdkDOZLogFactory extends DOZLogFactory {

	@Override
	public DOZLog create(Class<?> clazz) {
		return new DOZJdkLog(clazz);
	}

}
