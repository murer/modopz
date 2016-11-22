package com.murerz.modopz.core.exec;

import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.exec.MOStartProcessMessage.StartProcessResult;

public class MOStartProcessMessage extends MOProcessMessage<StartProcessResult> {

	public static class StartProcessResult {
		private long id;

		public long getId() {
			return id;
		}

		public StartProcessResult setId(long id) {
			this.id = id;
			return this;
		}

	}

	private List<String> cmds = new ArrayList<String>();

	public List<String> getCmds() {
		return cmds;
	}

	public MOStartProcessMessage setCmds(List<String> cmds) {
		this.cmds = cmds;
		return this;
	}

	@Override
	public Class<StartProcessResult> respType() {
		return StartProcessResult.class;
	}

}
