package com.murerz.dsopz.core;

public class DOZBuilder {

	private DOZLogFactory logFactory;

	@SuppressWarnings("unchecked")
	public <T> T build(Class<T> clazz) {
		try {
			DOZLog log = logFactory.createLog(clazz);
			DOZBuildable ret = (DOZBuildable) clazz.newInstance();
			ret.setLog(log);
			return (T) ret;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
