package com.vulpes.ai;

import java.util.Arrays;
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
        Integer productionCapacity = store.get(produced) + PRODUCED_RESOURCES;
        productionCapacity = productionCapacity > MAX_STORAGE ? MAX_STORAGE : productionCapacity;
        store.put(produced, productionCapacity);
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
            if (calculatePriceFor(resource) < gold)
                for (int i = store.get(resource); i < MIN_WANTED; i++) {
                    getMarketByResource(resource).buy(new Offer(this, calculatePriceFor(resource)));
                }
        }
    }

    private Double calculatePriceFor(Resource resource) {
        Double priceModification = MIN_WANTED.floatValue() / (store.get(resource).floatValue() + 1.0) + 1.0 / MIN_WANTED.floatValue();
        priceModification = priceModification * Math.random();
        return getMarketByResource(resource).getLastDayPrice() + priceModification;
    }

    @Override
    public String toString() {
        final String[] result = {""};
        result[0] += name + " has ";

        Arrays.stream(Resource.values()).forEach(resource -> result[0] += store.get(resource) + " " + resource.name() + "; ");

        result[0] += gold + " gold and produced " + produced;
        return result[0];
    }
}
