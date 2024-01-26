package com.example.designpattern.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理者只能看到窄接口，不允许修改备忘录对象
 */
class CareTaker {

    private List<IMemento> mementoList = new ArrayList<>();

    public void add(IMemento memento) {
        mementoList.add(memento);
    }

    public IMemento get(int index) {
        if (index >= mementoList.size()) return null;
        return mementoList.get(index);
    }

}
