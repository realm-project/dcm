package net.realmproject.dcm.device.test.commands;


import java.io.Serializable;


public class Incrementer implements Serializable {

    public Incrementer() {
        // TODO Auto-generated constructor stub
    }

    public Incrementer(int increment) {
        this.increment = increment;
    }

    public int increment = 0;
    public int decrement = 0;
}