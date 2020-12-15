package com.lizhengpeng.nio.reactor.multi;

public class MultiBootstrap {
    public static void main(String[] args) {
        MultiReactor reactor = new MultiReactor();
        reactor.bind(8080);
        System.out.println("服务端开始运行.....");
    }
}
