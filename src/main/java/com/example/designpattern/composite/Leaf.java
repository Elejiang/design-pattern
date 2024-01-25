package com.example.designpattern.composite;

class Leaf implements Component{
    private String name;
    public Leaf(String name) {
        this.name = name;
    }
    @Override
    public void add(Component c) {}
    @Override
    public void remove(Component c) {}
    @Override
    public Component getChild(int i) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void operation() {
        // TODO Auto-generated method stub
        System.out.println("树叶"+name+"：被访问！");
    }

}
