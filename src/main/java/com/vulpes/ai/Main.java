package com.vulpes.ai;

public class Main {
    public static void main(String[] args) {
       Globals.startMarkets();
       Globals.randomAgents(30);

       for (int i = 0; i < 100; i++){
           Globals.letThemSell();
           Globals.letThemBuy();
           Globals.dayEnds();
           System.out.println(" Day " + i);
           Globals.agentStatistics();

           if (Globals.agents.size() == 0) break;
       }

       System.out.println("Agents alive " + Globals.agents.size());
       Globals.agents.forEach(System.out::println);

       Globals.exportHistory();
    }
}
