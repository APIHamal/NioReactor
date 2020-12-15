package com.lizhengpeng.nio.reactor.single;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Nio模型中Reactor实现
 * @author lizhengpeng
 */
public class Acceptor implements Runnable{

    private volatile ServerSocketChannel serverSocketChannel;
    private volatile Selector selector;

    public Acceptor(ServerSocketChannel serverSocketChannel,Selector selector){
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try{
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
            selectionKey.attach(new ChannelHandler(socketChannel, selectionKey));
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
