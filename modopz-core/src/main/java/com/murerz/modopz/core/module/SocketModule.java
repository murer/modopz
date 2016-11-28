package com.murerz.modopz.core.module;

import com.murerz.modopz.core.service.Param;
import com.murerz.modopz.core.util.Status;

public interface SocketModule extends Module {

	String connect(@Param("destHost") String destHost, @Param("destPort") Integer destPort);

	byte[] read(@Param("dest") String dest);

	Status write(@Param("dest") String dest, @Param("data") byte[] n);

	Status destroy(@Param("dest") String dest);

}
