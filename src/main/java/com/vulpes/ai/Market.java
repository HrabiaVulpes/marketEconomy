package com.vulpes.ai;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Market {
    public List<Double> history = new ArrayList<>();
    private Resource marketResource;
    private Double lastDayPrice = 10.0;
    private List<Offer> buyOffers = new ArrayList<>();
    private List<Offer> sellOffers = new ArrayList<>();

    public Market(Resource marketResource) {
        this.marketResource = marketResource;
    }

    public void finalizeOffer(Offer offer, Agent buyer, Agent seller) {
        buyer.store.put(marketResource, buyer.store.get(marketResource) + 1);
        seller.gold = seller.gold + offer.price;
    }

    public void returnSellOffer(Offer offer) {
        offer.who.store.put(marketResource, offer.who.store.get(marketResource) + 1);
    }

    public void returnBuyOffer(Offer offer) {
        offer.who.gold = offer.who.gold + offer.price;
    }

    public boolean buy(Offer offer) {
        Offer lowestSell = sellOffers.stream()
                .min(Comparator.comparingDouble(sellOffer -> sellOffer.price))
                .orElse(null);

        if (lowestSell == null || lowestSell.price > offer.price) {
            offer.who.gold = offer.who.gold - offer.price;
            buyOffers.add(offer);
            return false;
        }

        offer.who.gold = offer.who.gold - lowestSell.price;
        finalizeOffer(offer, offer.who, lowestSell.who);
        sellOffers.remove(lowestSell);
        return true;
    }

    public boolean sell(Offer offer) {
        Offer highestBuy = buyOffers.stream()
                .min(Comparator.comparingDouble(buyOffer -> buyOffer.price))
                .orElse(null);

        offer.who.store.put(marketResource, offer.who.store.get(marketResource) - 1);

        if (highestBuy == null || highestBuy.price < offer.price) {
            sellOffers.add(offer);
            return false;
        }

        finalizeOffer(offer, highestBuy.who, offer.who);
        buyOffers.remove(highestBuy);
        return true;
    }

    public void dayEnds() {
        Offer highestSell = sellOffers.stream()
                .max(Comparator.comparingDouble(Offer::getPrice))
                .orElse(null);

        Offer lowestBuy = buyOffers.stream()
                .min(Comparator.comparingDouble(Offer::getPrice))
                .orElse(null);

        if (highestSell != null && lowestBuy != null) lastDayPrice = (highestSell.price + lowestBuy.price) / 2;
        if (highestSell != null && lowestBuy == null) lastDayPrice = highestSell.price;
        if (highestSell == null && lowestBuy != null) lastDayPrice = lowestBuy.price;

        history.add(lastDayPrice);

        clearSellOffers();
        clearBuyOffers();
    }

    public void clearSellOffers() {
        while (!sellOffers.isEmpty()) {
            returnSellOffer(sellOffers.get(0));
            sellOffers.remove(0);
        }
    }

    public void clearBuyOffers() {
        while (!buyOffers.isEmpty()) {
            returnBuyOffer(buyOffers.get(0));
            buyOffers.remove(0);
        }
    }

    public Double getLastDayPrice() {
        return lastDayPrice;
    }

    public Resource getMarketResource() {
        return marketResource;
    }

    public void exportData(FileWriter writer){
        try {
            writer.write(marketResource.name() +";");
            for (Double entry : history){
                //TODO: replace dot with comma
                writer.write(entry + ";");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
