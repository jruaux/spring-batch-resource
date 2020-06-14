package org.springframework.batch.item.resource;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public class StandardInputResource extends InputStreamResource {

    public StandardInputResource() {
        super(System.in, "stdin");
    }

    @Override
    public InputStream getInputStream() {
        return System.in;
    }
}