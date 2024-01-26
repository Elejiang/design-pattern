package com.example.designpattern.mediator;

import java.util.ArrayList;
import java.util.List;

class ConcreteMediator implements Mediator {
    private List<Colleague> colleagues = new ArrayList<>();
    @Override
    public void register(Colleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMedium(this);
        }
    }
    @Override
    public void relay(String from, String to, String ad) {
        for (Colleague cl : colleagues) {
            String name = cl.getName();
            if (name.equals(to)) {
                cl.receive(from, ad);
            }
        }
    }
}
