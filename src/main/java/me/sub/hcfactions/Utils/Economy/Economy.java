package me.sub.hcfactions.Utils.Economy;

import me.sub.hcfactions.Files.Players.Players;

import java.math.BigDecimal;

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
        double balance = getBalance();
        if (balance - v >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isValidNumber(Double v) {
        String number = String.valueOf(v);
        String[] parts = number.split("\\.");
        String other = parts[1];
        other = other.replace(".", "");
        if (other.length() > 2) {
            return false;
        } else {
            return true;
        }
    }
}
