package com.murerz.modopz.core.exec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;
import com.murerz.modopz.core.kernel.MOResult;

public class MOListProcessCommand implements MOCommand {

	public static class MOListProcessResult implements MOResult {
		private List<Long> prcs = new ArrayList<Long>();

		public List<Long> getPrcs() {
			return prcs;
		}

		public MOListProcessResult setPrcs(List<Long> prcs) {
			this.prcs = prcs;
			return this;
		}
	}

	public MOListProcessResult execute(MOKernel kernel) {
		MOProcessModule module = kernel.module(MOProcessModule.class);
		List<Long> ids = new ArrayList<Long>();
		ids.addAll(module.processIds());
		Collections.sort(ids);
		return new MOListProcessResult().setPrcs(ids);
	}

}
