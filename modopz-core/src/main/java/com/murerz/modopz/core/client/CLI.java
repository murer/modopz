package com.murerz.modopz.core.client;

import java.io.Closeable;

import com.murerz.modopz.core.service.CLIConfigurable;
import com.murerz.modopz.core.service.HttpProxyService;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.util.Reflect;
import com.murerz.modopz.core.util.Util;

public abstract class CLI implements CLIConfigurable, Closeable {

	private Service service;

	public abstract void exec();

	public void config() {
		configService();
	}

	private void configService() {
		String sn = ClientConfig.me().prop("modopz.service", HttpProxyService.class.getName());
		this.service = Reflect.newInstance(sn, Service.class);
		if (this.service instanceof CLIConfigurable) {
			((CLIConfigurable) this.service).config();
		}
	}

	public static void main(String[] args) {
		cli();
	}

	public static void cli() {
		String name = ClientConfig.me().prop("modopz.client");
		CLI cli = Reflect.newInstance(name, CLI.class);
		try {
			cli.config();
			cli.start();
			cli.exec();
		} finally {
			Util.close(cli);
		}
	}

	public void start() {
		service.start();
	}

	public void close() {
		Util.close(service);
	}

	public Service service() {
		return service;
	}

}
