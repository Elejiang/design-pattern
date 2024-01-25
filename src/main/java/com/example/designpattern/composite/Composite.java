package com.example.designpattern.composite;

import java.util.ArrayList;


class Composite implements Component {
    private String name;
    private ArrayList<Component> children = new ArrayList<Component>();
    public Composite(String name) {
        this.name = name;
    }
    public void add(Component c) {
        children.add(c);
    }
    public void remove(Component c) {
        children.remove(c);
    }
    public Component getChild(int i) {
        return children.get(i);
    }
    public void operation() {
        for (Component component : children) {
            component.operation();
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
