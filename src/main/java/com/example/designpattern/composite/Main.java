package com.example.designpattern.composite;

class Main {
    public static void main(String[] args) {
        Composite root = new Composite("根节点");
        root.add(new Leaf("叶子节点1"));
        root.add(new Leaf("叶子节点2"));
        root.add(new Leaf("叶子节点3"));
        Composite composite1 = new Composite("节点1");
        root.add(composite1);
        composite1.add(new Leaf("叶子节点4"));
        composite1.add(new Leaf("叶子节点5"));
        root.operation();
    }
}
