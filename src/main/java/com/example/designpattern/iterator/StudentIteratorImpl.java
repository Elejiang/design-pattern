package com.example.designpattern.iterator;

import java.util.List;

class StudentIteratorImpl implements Iterator {
    private List<Student> list;
    private int position = 0;

    public StudentIteratorImpl(List<Student> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position < list.size();
    }

    @Override
    public Object next() {
        Student currentStudent = list.get(position);
        position++;
        return currentStudent;
    }
}
