package org.springframework.batch.item.resource;

public class StandardOutputResource extends OutputStreamResource {

	public StandardOutputResource() {
		super(System.out, "stdout");
	}
	
}