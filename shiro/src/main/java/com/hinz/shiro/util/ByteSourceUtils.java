package com.hinz.shiro.util;

import org.apache.shiro.util.ByteSource;

import java.io.Serializable;

public class ByteSourceUtils {

    public static ByteSource bytes(byte[] bytes){
        return new SimpleByteSource(bytes);
    }

    public static ByteSource bytes(String obj){
        return new SimpleByteSource(obj.getBytes());
    }
}

class SimpleByteSource extends org.apache.shiro.util.SimpleByteSource
        implements Serializable {

    private static final long serialVersionUID = 5528101080905698238L;

    public SimpleByteSource(byte[] bytes) {
        super(bytes);
    }
}
