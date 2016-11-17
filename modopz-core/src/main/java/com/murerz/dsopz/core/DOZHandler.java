package com.murerz.dsopz.core;

import java.io.Closeable;

public abstract class DOZHandler<T> implements Closeable {

	public DOZHandler(DOZConfig config) {
		
	}
	
	public abstract Class<T> spec();

	public abstract void onCommand(T command);

	public abstract void start();

}
