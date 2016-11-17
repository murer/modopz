package com.murerz.dsopz.core;

import java.io.Closeable;

public interface DOZHandler<T> extends Closeable {

	public Class<T> spec();

	public void onCommand(T command);

}
