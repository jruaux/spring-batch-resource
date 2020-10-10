package org.springframework.batch.item.resource;

import org.springframework.core.io.InputStreamResource;

public class StandardInputResource extends InputStreamResource {

	public StandardInputResource() {
		super(System.in, "stdin");
	}

}