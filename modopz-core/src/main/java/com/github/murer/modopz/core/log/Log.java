package com.github.murer.modopz.core.log;

public interface Log {

	void error(String msg, Exception e);

	void info(String msg);

	void error(String msg);

}
