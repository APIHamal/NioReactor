package com.lizhengpeng.nio.reactor.master;

import com.lizhengpeng.nio.reactor.multi.MultiChannelHandler;
import com.lizhengpeng.nio.reactor.multi.MultiDispatcher;
import com.lizhengpeng.nio.reactor.single.ChannelHandler;
import com.lizhengpeng.nio.reactor.single.Dispatcher;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 从Reactor模式
 * @author lizhengpeng
 */
public class SlaveReactor implements Runnable{

    private volatile MasterSlavePipeline pipeline;
    private Selector selector;

    public SlaveReactor(MasterSlavePipeline pipeline){
        this.pipeline = pipeline;
    }

    public void runTask() throws IOException {
        selector = Selector.open();
        MultiDispatcher dispatcher = new MultiDispatcher(selector);
        dispatcher.dispatcher();
        Thread actualTask = new Thread(SlaveReactor.this);
        actualTask.start();
    }

    @Override
    public void run() {
        while (true) {
            try{
                SocketChannel clientChannel = pipeline.pullSocketChannel();
                clientChannel.configureBlocking(false);
                SelectionKey selectionKey = clientChannel.register(selector, SelectionKey.OP_READ);
                ChannelHandler channelHandler = new ChannelHandler(clientChannel, selectionKey);
                selectionKey.attach(new MultiChannelHandler(channelHandler));
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }
}
