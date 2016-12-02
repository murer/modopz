package com.github.murer.modopz.core.module;

import com.github.murer.modopz.core.process.MOProcessStatus;
import com.github.murer.modopz.core.process.ProcessCommand;
import com.github.murer.modopz.core.service.Param;
import com.github.murer.modopz.core.util.Status;

public interface ProcessModule extends Module {

	Long create(@Param("cmd") ProcessCommand cmd);

	MOProcessStatus status(@Param("id") Long id, @Param("timeout") Long timeout);

	Status write(@Param("id") Long id, @Param("timeout") byte[] data);

	Status destroy(@Param("id") Long id);

}
