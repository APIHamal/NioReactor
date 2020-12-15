package com.lizhengpeng.nio.reactor.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8080));
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello server".getBytes("UTF-8"));
        InputStream inputStream = socket.getInputStream();
        inputStream.read(new byte[10]);
        socket.close();
    }
}
