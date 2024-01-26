package com.example.designpattern.chainofresponsibility;

class GroupLeader extends Handler {
    public GroupLeader() {
        super(Handler.NUM_ONE, Handler.NUM_THREE);
    }
    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天," + leave.getContent() + "。");
        System.out.println("小组长审批：同意。");
    }
}
