package com.example.designpattern.chainofresponsibility;

class Main {
    public static void main(String[] args) {
        LeaveRequest leave1 = new LeaveRequest("张三",1,"身体不适");
        LeaveRequest leave2 = new LeaveRequest("李四",5,"回家种地");
        LeaveRequest leave3 = new LeaveRequest("王五",9,"家里老母猪生了");

        GroupLeader groupLeader = new GroupLeader();
        Manager manager = new Manager();
        GeneralManager generalManager = new GeneralManager();

        groupLeader.setNextHandler(manager);
        manager.setNextHandler(generalManager);

        groupLeader.submit(leave1);
        groupLeader.submit(leave2);
        groupLeader.submit(leave3);
    }
}
