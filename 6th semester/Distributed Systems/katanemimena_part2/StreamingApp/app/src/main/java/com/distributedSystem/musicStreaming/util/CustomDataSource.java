//package com.distributedSystem.musicStreaming.util;
//
//import android.media.MediaDataSource;
//
//import java.io.IOException;
//
//public class CustomDataSource extends MediaDataSource {
//
//    byte[] data;
//
//    public CustomDataSource(byte[] data) {
//
//        this.data = data;
//    }
//
//    @Override
//    public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
//        System.arraycopy(data, (int)position, buffer, offset, size);
//        return size;
//    }
//
//    @Override
//    public long getSize() throws IOException {
//        return data.length;
//    }
//
//    @Override
//    public void close() throws IOException {
//
//    }
//}
