package com.murerz.modopz.core.module;

import com.murerz.modopz.core.process.MOProcessStatus;
import com.murerz.modopz.core.process.ProcessCommand;
import com.murerz.modopz.core.service.Param;
import com.murerz.modopz.core.util.Status;

public interface ProcessModule extends Module {

	Long create(@Param("cmd") ProcessCommand cmd);

	MOProcessStatus status(@Param("id") Long id, @Param("timeout") Long timeout);

	Status write(@Param("id") Long id, @Param("timeout") byte[] data);

	Status destroy(@Param("id") Long id);

}
