package com.lizhengpeng.nio.reactor.multi;

import com.lizhengpeng.nio.reactor.single.ChannelHandler;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Nio模型中Reactor实现
 * @author lizhengpeng
 */
public class MultiAcceptor implements Runnable{

    private volatile ServerSocketChannel serverSocketChannel;
    private volatile Selector selector;

    public MultiAcceptor(ServerSocketChannel serverSocketChannel, Selector selector){
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try{
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
            ChannelHandler actualTask = new ChannelHandler(socketChannel, selectionKey);
            MultiChannelHandler channelHandler = new MultiChannelHandler(actualTask);
            selectionKey.attach(channelHandler);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
