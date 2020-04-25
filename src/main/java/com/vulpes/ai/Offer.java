package com.vulpes.ai;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(who, offer.who) &&
                price.equals(offer.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who, price);
    }
}
