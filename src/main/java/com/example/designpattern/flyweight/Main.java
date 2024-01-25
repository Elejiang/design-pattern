package com.example.designpattern.flyweight;

class Main {
    public static void main(String[] args) {
        BoxFactory factory = BoxFactory.getInstance();
        AbstractBox iBox1 = factory.getBox("I");
        AbstractBox iBox2 = factory.getBox("I");
        AbstractBox lBox1 = factory.getBox("L");
        AbstractBox lBox2 = factory.getBox("L");
        AbstractBox oBox1 = factory.getBox("O");
        AbstractBox oBox2 = factory.getBox("O");
        System.out.println(iBox1 == iBox2);
        System.out.println(lBox1 == lBox2);
        System.out.println(oBox1 == oBox2);
        iBox1.display("红色");
        iBox2.display("绿色");
        lBox1.display("蓝色");
        lBox2.display("紫色");
        oBox1.display("粉色");
        oBox2.display("白色");
    }
}
