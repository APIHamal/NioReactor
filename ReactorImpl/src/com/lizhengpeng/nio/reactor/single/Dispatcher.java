package com.lizhengpeng.nio.reactor.single;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor中分发器的实现
 * @author lizhengpeng
 */
public class Dispatcher implements Runnable{

    private Selector selector;
    private volatile boolean stopFlag;
    private Thread currentThread;

    public Dispatcher(Selector selector){
        this.selector = selector;
    }

    /**
     * 调用线程的中断方法请求线程中断
     */
    public void shutdownServer(){
        this.stopFlag = true;
        currentThread.interrupt();
    }

    public void dispatcher(){
        if(currentThread != null){
            throw new IllegalArgumentException("线程已经运行");
        }
        currentThread = new Thread(Dispatcher.this);
        currentThread.start();
    }

    @Override
    public void run() {
        try{
            while(!stopFlag){
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keySet.iterator();
                while(keyIterator.hasNext()){
                    SelectionKey key = keyIterator.next();
                    Runnable runnable = (Runnable)key.attachment();
                    runnable.run();
                    keyIterator.remove();
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            currentThread = null;
        }
    }
}
