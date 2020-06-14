package com.redislabs.riot.file;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.WritableResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OutputStreamResource extends AbstractResource implements WritableResource {

    private String desc;
    private OutputStream outStream;

    public OutputStreamResource(OutputStream outStream) {
        this(outStream, "");
    }

    public OutputStreamResource(OutputStream outStream, String desc) {
        this.outStream = outStream;
        this.desc = desc;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.outStream;
    }

    @Nullable
    @Override
    public String getDescription() {
        return this.desc;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException("Unable to create input stream.");
    }

    @Override
    public boolean isWritable() {
        return true;
    }

}
