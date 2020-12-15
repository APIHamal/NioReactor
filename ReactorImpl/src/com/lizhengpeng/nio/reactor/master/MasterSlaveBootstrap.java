package com.lizhengpeng.nio.reactor.master;

import java.io.IOException;

public class MasterSlaveBootstrap {
    public static void main(String[] args) throws IOException {
        MasterSlavePipeline pipeline = new MasterSlavePipeline();
        SlaveReactor slaveReactor = new SlaveReactor(pipeline);
        slaveReactor.runTask();
        MasterReactor masterReactor = new MasterReactor(pipeline);
        masterReactor.bind(8080);
        System.out.println("服务端开始执行.....");
    }
}
