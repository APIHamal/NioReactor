package com.lizhengpeng.nio.reactor.multi;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiDispatcher implements Runnable {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(16);
    private volatile Selector selector;

    public MultiDispatcher(Selector selector){
        this.selector = selector;
    }

    public void dispatcher(){
        Thread newThread = new Thread(MultiDispatcher.this);
        newThread.start();
    }

    @Override
    public void run() {
        try {
            while(true){
                selector.selectNow();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.attachment() != null){
                        iterator.remove();
                        Runnable runnable = (Runnable) selectionKey.attachment();
                        runnable.run();
                    }
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
