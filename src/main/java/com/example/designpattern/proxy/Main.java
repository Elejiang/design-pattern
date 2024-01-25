package com.example.designpattern.proxy;

class Main {
    public static void main(String[] args) {
        // 静态代理
        ProxyPoint pp = new ProxyPoint();
        pp.sell();
        // JDK动态代理
        JDKProxyFactory jdkProxyFactory = new JDKProxyFactory();
        SellTickets proxyObject = jdkProxyFactory.getProxyObject();
        proxyObject.sell();
        // CGLIB动态代理
        CGLIBProxyFactory cglibProxyFactory = new CGLIBProxyFactory();
        TrainStation proxyObject1 = cglibProxyFactory.getProxyObject();
        proxyObject1.sell();
    }
}
