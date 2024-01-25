package com.example.designpattern.iterator;

class Main {
    public static void main(String[] args) {
        Student student1 = new Student("张三");
        Student student2 = new Student("李四");
        Student student3 = new Student("王五");
        Aggregate aggregate = new StudentAggregateImpl();
        aggregate.add(student1);
        aggregate.add(student2);
        aggregate.add(student3);
        Iterator iterator = aggregate.getIterator();
        while (iterator.hasNext()) {
            Student student = (Student) iterator.next();
            System.out.println(student.getName());
        }
    }
}
