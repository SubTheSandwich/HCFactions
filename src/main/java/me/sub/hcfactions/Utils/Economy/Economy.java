package me.sub.hcfactions.Utils.Economy;

import me.sub.hcfactions.Files.Players.Players;
public class Economy {

    Players players;

    public Economy(String UUID) {
        players = new Players(UUID);
    }

    public Double getBalance() {
        return players.get().getDouble("balance");
    }

    public void setBalance(Double v) {
        players.get().set("balance", v);
        players.save();
    }

    public void withdrawBalance(Double v) {
        players.get().set("balance", players.get().getDouble("balance") - v);
        players.save();
    }

    public void depositBalance(Double v) {
        players.get().set("balance", players.get().getDouble("balance") + v);
        players.save();
    }

    public Boolean has(Double v) {
        return getBalance() >= v;
    }

    public Boolean isValidNumber(Double v) {
        String number = String.valueOf(v);
        String[] parts = number.split("\\.");
        String other = parts[1];
        other = other.replace(".", "");
        return other.length() <= 2;
    }
}
