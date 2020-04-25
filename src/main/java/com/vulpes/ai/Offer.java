package com.vulpes.ai;

public class Offer {
    Agent who;
    Double price;

    Offer(Agent who, Double price) {
        this.who = who;
        this.price = price;
    }

    Agent getWho() {
        return who;
    }

    Double getPrice() {
        return price;
    }
}
