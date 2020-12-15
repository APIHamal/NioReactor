package com.lizhengpeng.nio.reactor.single;

/**
 * 引导程序
 * @author lizhengpeng
 */
public class Bootstrap {
    public static void main(String[] args) {
        Reactor nioServer = new Reactor();
        nioServer.bind(8080);
        System.out.println("服务器开始运行.....");
    }
}
