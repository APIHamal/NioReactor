package com.lizhengpeng.nio.reactor.master;

import com.lizhengpeng.nio.reactor.single.Acceptor;
import com.lizhengpeng.nio.reactor.single.ChannelHandler;
import com.lizhengpeng.nio.reactor.single.Dispatcher;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 主Reactor模式
 * @author lizhengpeng
 */
public class MasterReactor {

    private volatile MasterSlavePipeline pipeline;

    public MasterReactor(MasterSlavePipeline pipeline){
        this.pipeline = pipeline;
    }

    public void bind(int port){
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            Selector selector = Selector.open();
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new MasterAcceptor(serverSocketChannel, pipeline));
            Dispatcher dispatcher = new Dispatcher(selector);
            dispatcher.dispatcher();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
