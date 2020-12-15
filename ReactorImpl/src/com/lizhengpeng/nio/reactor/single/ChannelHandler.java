package com.lizhengpeng.nio.reactor.single;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ChannelHandler implements Runnable {

    private volatile SocketChannel socketChannel;
    private volatile SelectionKey key;

    public ChannelHandler(SocketChannel socketChannel,SelectionKey key){
        this.socketChannel = socketChannel;
        this.key = key;
    }

    /**
     * 指定客户端执行逻辑
     */
    @Override
    public void run() {
        try{
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            if(byteBuffer.hasRemaining()){
                System.out.println("接收到客户端信息  "+new String(byteBuffer.array(), "UTF-8"));
                socketChannel.write(ByteBuffer.wrap("hello client".getBytes("UTF-8")));
                byte[] context = "hello client,这里测试非阻塞的写入功能".getBytes("UTF-8");
                System.out.println("写入长度  "+socketChannel.write(ByteBuffer.wrap(context)));
            }
        }catch (Throwable e){
            key.cancel();
            e.printStackTrace();
        }
    }
}
