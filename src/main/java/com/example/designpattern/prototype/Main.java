package com.example.designpattern.prototype;

class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 浅拷贝
        Person zhangsan = new Person("张三", 23);
        Person zhangsanClone = zhangsan.clone();
        System.out.println(zhangsan.toString());
        System.out.println(zhangsanClone.toString());
    }
}
