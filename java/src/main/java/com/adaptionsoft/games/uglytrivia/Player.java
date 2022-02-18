package com.adaptionsoft.games.uglytrivia;

public class Player {

    private String name;
    private int purse;
    private int place;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        purse = 0;
        place = 0;
        inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurse() {
        return purse;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int roll) {
        place = place + roll;

        if (place > 11)
            place = place - 12;

    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public void addCoinToPurse() {
        purse ++;
    }
}
