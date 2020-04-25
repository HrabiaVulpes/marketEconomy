package com.vulpes.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Globals {
    public static List<Agent> agents = new ArrayList<>();
    public static List<Market> markets = new ArrayList<>();

    public static Integer START_RESOURCES = 4;
    public static Double START_GOLD = 100.0;
    public static Integer PRODUCED_RESOURCES = 4;
    public static Integer CONSUMED_RESOURCES = 1;
    public static Integer MIN_WANTED = 3;
    public static Integer MAX_WANTED = 10;

    public static Market getMarketByResource(Resource resource){
        return markets.stream()
                .filter(market -> market.getMarketResource() == resource)
                .findFirst()
                .orElse(null);
    }

    public static int randomBetween(int min, int max){
        return (int) (Math.random() * (max-min+1) + min);
    }

    public static void randomAgents(int amount){
        Resource[] resourceTable = Resource.values();
        for (int i = 0; i < amount; i++){
            agents.add(new Agent("Agent_" + i, resourceTable[randomBetween(0, resourceTable.length-1)]));
        }
    }

    public static void equalAgents(int amountOfEach){
        for (Resource resource : Resource.values()){
            for (int i = 0; i < amountOfEach; i++){
                agents.add(new Agent("Agent_" + i, resource));
            }
        }
    }

    public static void startMarkets(){
        for (Resource resource : Resource.values()){
            markets.add(new Market(resource));
        }
    }

    public static void letThemSell(){
        agents.forEach(Agent::sell);
    }

    public static void letThemBuy(){
        agents.forEach(Agent::buy);
    }

    public static void dayEnds(){
        agents.forEach(Agent::dayEnd);

        agents = agents.stream()
                .filter(agent -> agent.HP > 0)
                .collect(Collectors.toList());

        markets.forEach(Market::dayEnds);
    }

    public static void agentStatistics(){
        System.out.println("--- AGENT STATS ---");
        System.out.println("Total Agents Alive: " + agents.size());
        for (Resource resource : Resource.values()){
            System.out.println("Agents producing " + resource + ": " + agents.stream().filter(agent -> agent.produced == resource).count());
        }
        System.out.println("Total Agent Gld: " + agents.stream().mapToDouble(agent -> agent.gold).sum());
        for (Resource resource : Resource.values()){
            System.out.println("Agents producing " + resource + " have: " + agents.stream().filter(agent -> agent.produced == resource).mapToDouble(agent -> agent.gold).sum());
        }
        System.out.println("-------------------");
    }
}
