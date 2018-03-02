package com.yukselproje.okurular.incompany.Memento;

public class Originator {
    private String state;
    private static Originator originator = new Originator();

    private Originator() {
    }

    public static Originator getInstance() {
        return originator;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
}