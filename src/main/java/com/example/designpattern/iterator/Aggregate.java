package com.example.designpattern.iterator;

interface Aggregate {
    void add(Object obj);

    void remove(Object obj);

    Iterator getIterator();
}
