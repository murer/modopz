package com.murerz.modopz.core.module;

import com.murerz.modopz.core.process.MOProcessStatus;
import com.murerz.modopz.core.process.ProcessCommand;

public interface ProcessModule extends Module {

	Long create(ProcessCommand setCmds);

	MOProcessStatus status(long l);

}
