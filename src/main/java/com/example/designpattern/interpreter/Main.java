package com.example.designpattern.interpreter;

class Main {
    public static void main(String[] args) {
        Client client = new Client("select id, name from student where id = 1");
        String interpretResult = client.getInterpretResult();
        System.out.println(interpretResult);
    }
}
