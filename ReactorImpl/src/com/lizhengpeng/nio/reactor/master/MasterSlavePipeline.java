package com.lizhengpeng.nio.reactor.master;

import java.lang.reflect.Array;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Mater-salve主从通信
 * @author lizhengpeng
 */
public class MasterSlavePipeline {

    private volatile LinkedList<SocketChannel> list = new LinkedList<>();

    public void pushSocketChannel(SocketChannel socketChannel) throws InterruptedException {
        synchronized (list){
            list.push(socketChannel);
            list.notifyAll();
        }
    }

    public SocketChannel pullSocketChannel() throws InterruptedException {
        synchronized (list){
            while(true){
                if(list.isEmpty()){
                    list.wait();
                }else {
                    SocketChannel channel = list.pop();
                    return channel;
                }
            }
        }
    }

}
