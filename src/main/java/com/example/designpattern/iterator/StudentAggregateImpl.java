package com.example.designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

class StudentAggregateImpl implements Aggregate {

    private List<Student> list = new ArrayList<>();

    @Override
    public void add(Object obj) {
        this.list.add((Student) obj);
    }

    @Override
    public void remove(Object obj) {
        this.list.remove((Student) obj);
    }

    @Override
    public Iterator getIterator() {
        return new StudentIteratorImpl(list);
    }
}
