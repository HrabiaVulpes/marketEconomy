package com.vulpes.ai;

import java.util.HashMap;
import java.util.Map;

import static com.vulpes.ai.Globals.*;

public class Agent {
    public Resource produced;
    Integer HP = 10;
    String name;
    Double gold;
    Map<Resource, Integer> store;

    public Agent(String name, Resource produced) {
        this.name = name;
        this.produced = produced;
        this.store = new HashMap<>();
        for (Resource resource : Resource.values()) {
            this.store.put(resource, START_RESOURCES);
        }
        this.gold = START_GOLD;
    }

    public void dayEnd() {
        for (Resource resource : Resource.values()) {
            Integer amount = store.get(resource);
            if (amount < CONSUMED_RESOURCES) {
                HP--;
            } else {
                store.put(resource, amount - CONSUMED_RESOURCES);
            }
        }
        store.put(produced, store.get(produced) + PRODUCED_RESOURCES);
    }


    public void sell() {
        for (Resource resource : Resource.values()) {
            while (store.get(resource) > MAX_WANTED) {
                getMarketByResource(resource).sell(new Offer(this, calculatePriceFor(resource)));
            }
        }
    }

    public void buy() {
        for (Resource resource : Resource.values()) {
            for (int i = store.get(resource); i < MIN_WANTED; i++) {
                getMarketByResource(resource).buy(new Offer(this, calculatePriceFor(resource)));
            }
        }
    }

    public Double calculatePriceFor(Resource resource) {
        return getMarketByResource(resource).getLastDayPrice()
                + (MIN_WANTED - store.get(resource)) * 0.1
                + (MAX_WANTED - store.get(resource)) * 0.1;
    }

    @Override
    public String toString() {
        return name + " has " + gold + " gold and produced " + produced;
    }
}