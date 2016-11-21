package com.murerz.dsopz.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOZExecHandler {

	private Map<Long, Prc> prcs = new HashMap<Long, Prc>();

	public static abstract class ExecCommand extends DOZCommand {

	}

	public static class StartCommand extends ExecCommand {
		private List<String> executable;
		private long result;

		public List<String> getExecutable() {
			return executable;
		}

		public StartCommand setExecutable(List<String> executable) {
			this.executable = executable;
			return this;
		}

		@Override
		public Long result() {
			return result;
		}
	}

	public static class PrcStatus {
		private long id;
		private Long status;
		private byte[] stdout;
		private byte[] stderr;
	}

	public static class StatusCommand extends ExecCommand {
		private long id;
		private byte[] stdin;
		private PrcStatus result;

		public long getId() {
			return id;
		}

		public StatusCommand setId(long id) {
			this.id = id;
			return this;
		}

		public byte[] getStdin() {
			return stdin;
		}

		public StatusCommand setStdin(byte[] stdin) {
			this.stdin = stdin;
			return this;
		}

		@Override
		public PrcStatus result() {
			return result;
		}
	}

	public void command(ExecCommand command) {
		if (command instanceof StartCommand) {
			start((StartCommand) command);
			return;
		}
		if (command instanceof StatusCommand) {
			status((StatusCommand) command);
			return;
		}
	}

	private static class Prc {
		private long id;
		private Process process;
		private long createdAt;
		private InputStream in;
		private InputStream err;
		private OutputStream out;
	}

	private void start(StartCommand command) {
		try {
			Prc prc = new Prc();
			prc.process = Runtime.getRuntime()
					.exec(command.getExecutable().toArray(new String[command.getExecutable().size()]));
			prc.createdAt = System.currentTimeMillis();
			prc.in = new BufferedInputStream(prc.process.getInputStream());
			prc.err = new BufferedInputStream(prc.process.getErrorStream());
			prc.out = new BufferedOutputStream(prc.process.getOutputStream());
			prc.id = DOZID.next();
			prcs.put(prc.id, prc);
			command.result = prc.id;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void status(StatusCommand command) {
		try {
			Prc prc = prcs.get(command.id);
			if (prc == null) {
				return;
			}
			if (command.stdin != null && command.stdin.length > 0) {
				prc.out.write(command.stdin);
				prc.out.flush();
			}
			command.result.stdout = DOZUtil.readAvailable(prc.in, 8 * 1024);
			command.result.stderr = DOZUtil.readAvailable(prc.err, 8 * 1024);
			command.result.status = DOZUtil.exitCode(prc.process);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
