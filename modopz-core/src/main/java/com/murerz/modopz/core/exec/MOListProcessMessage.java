package com.murerz.modopz.core.exec;

import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.exec.MOListProcessMessage.ListProcessResult;
import com.murerz.modopz.core.kernel.MOResultMessage;

public class MOListProcessMessage extends MOProcessMessage<ListProcessResult> {

	public static class ListProcessResult extends MOResultMessage {
		private List<Long> prcs = new ArrayList<Long>();

		public List<Long> getPrcs() {
			return prcs;
		}

		public ListProcessResult setPrcs(List<Long> prcs) {
			this.prcs = prcs;
			return this;
		}

	}

	@Override
	public Class<ListProcessResult> respType() {
		return ListProcessResult.class;
	}

}
