package com.example.designpattern.observer;

class Main {
    public static void main(String[] args) {
        SubscriptionSubject subscriptionSubject = new SubscriptionSubject();
        WechatUser wechatUser = new WechatUser("路人甲");
        QQUser qqUser = new QQUser("路人乙");
        subscriptionSubject.attach(wechatUser);
        subscriptionSubject.attach(qqUser);
        subscriptionSubject.notify("鸽鸽出新歌了");
    }
}
