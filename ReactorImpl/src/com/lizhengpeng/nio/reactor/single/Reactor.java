package com.lizhengpeng.nio.reactor.single;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Reactor实现
 * @author lizhengpeng
 */
public class Reactor {

    /**
     * 绑定监听端口
     * @param port
     */
    public void bind(int port){
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            Selector selector = Selector.open();
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor(serverSocketChannel,selector));
            Dispatcher dispatcher = new Dispatcher(selector);
            dispatcher.dispatcher();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
