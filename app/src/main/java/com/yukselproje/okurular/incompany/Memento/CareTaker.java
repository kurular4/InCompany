package com.yukselproje.okurular.incompany.Memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();
    private static CareTaker careTaker = new CareTaker();

    private CareTaker() {
    }

    public static CareTaker getInstance() {
        return careTaker;
    }

    public void add(Memento state) {
        mementoList.add(state);
    }

    public Memento get(int index) {
        return mementoList.get(index);
    }
}