package com.lizhengpeng.nio.reactor.master;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MasterAcceptor implements Runnable {

    private volatile MasterSlavePipeline masterSlavePipeline;
    private volatile ServerSocketChannel serverSocketChannel;

    public MasterAcceptor(ServerSocketChannel serverSocketChannel, MasterSlavePipeline masterSlavePipeline){
        this.serverSocketChannel = serverSocketChannel;
        this.masterSlavePipeline = masterSlavePipeline;
    }

    @Override
    public void run() {
        try{
            SocketChannel clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);
            masterSlavePipeline.pushSocketChannel(clientChannel);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
