package com.murerz.modopz.client.process;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.murerz.modopz.client.MOHttpClient;
import com.murerz.modopz.core.process.MODataProcessResult;
import com.murerz.modopz.core.process.MOStartProcessCommand;
import com.murerz.modopz.core.process.MOStatusProcessCommand;
import com.murerz.modopz.core.util.MOUtil;

public class MOProcessClient {

	private OutputStream out;
	private InputStream in;
	private OutputStream err;
	private MOHttpClient client;

	private Long id;

	public MOProcessClient setOut(OutputStream out) {
		this.out = out;
		return this;
	}

	public MOProcessClient setIn(InputStream in) {
		this.in = in;
		return this;
	}

	public MOProcessClient setErr(OutputStream err) {
		this.err = err;
		return this;
	}

	public MOProcessClient setClient(MOHttpClient client) {
		this.client = client;
		return this;
	}

	public Integer execute(MOStartProcessCommand start) {
		this.id = client.json(start, MODataProcessResult.class).getId();

		while (true) {
			MOStatusProcessCommand status = new MOStatusProcessCommand();
			status.setId(id);
			status.setStdin(MOUtil.readAvailable(in, 8 * 1024));
			status.setWaitFor(100L);
			MODataProcessResult result = client.json(status, MODataProcessResult.class);
			if (result.getStdout() != null) {
				MOUtil.writeFlush(out, result.getStdout());
			}
			if (result.getStderr() != null) {
				MOUtil.writeFlush(err, result.getStderr());
			}
			if (result.getCode() != null) {
				return result.getCode();
			}
			MOUtil.sleep(200L);
		}

	}

	public static void main(String[] args) {
		MOProcessClient client = new MOProcessClient();
		client.setIn(System.in);
		client.setOut(System.out);
		client.setErr(System.err);
		client.setClient(MOHttpClient.create());

		// MOStartProcessCommand command =
		// MOJson.parse(System.getProperty("cmd"), MOStartProcessCommand.class);
		MOStartProcessCommand command = new MOStartProcessCommand().setCmds(Arrays.asList("/bin/bash"));
		Integer code = client.execute(command);
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		System.exit(code);
	}

}
