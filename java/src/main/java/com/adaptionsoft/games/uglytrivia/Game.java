package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Category> categories = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private Category currentCategory;
    private int currentPlayerIndex = 0;

    public Game() {
        categories.add(new PopCategory(new int[]{0, 4, 8}));
        categories.add(new ScienceCategory(new int[]{1, 5, 9}));
        categories.add(new SportsCategory(new int[]{2, 6, 10}));
        categories.add(new RockCategory(new int[]{3, 7, 11}));
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        if (currentPlayer == null) {
            currentPlayer = players.get(0);
        }

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + howManyPlayers());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            penaltyBoxTurn(roll);
        } else {
            regularTurn(roll);
        }

    }

    private void penaltyBoxTurn(int roll) {
        boolean shouldGetOutOfPenaltyBox = roll % 2 != 0;
        if (shouldGetOutOfPenaltyBox) {
            System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
            currentPlayer.setInPenaltyBox(false);
            regularTurn(roll);

        } else {
            System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
        }
    }

    private void setPlayerPlace(int roll) {
        currentPlayer.setPlace(roll);
    }

    private void regularTurn(int roll) {
        setPlayerPlace(roll);
        setCurrentCategory();

        System.out.println(currentPlayer.getName()
                + "'s new location is "
                + currentPlayer.getPlace());
        System.out.println("The category is " + currentCategory.getName());
        askQuestion();
    }

    private void setCurrentCategory() {
        for (Category category: categories) {
            boolean isPlayerOnCategoryPlace = category.belongsTo(currentPlayer.getPlace());

            if(isPlayerOnCategoryPlace){
                currentCategory = category;
                break;
            }
        }
    }

    private void askQuestion() {
        System.out.println(currentCategory.getNextQuestion());
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer.isInPenaltyBox()) {
            setNextPlayer();
            return true;
        }

        currentPlayer.addCoinToPurse();
        boolean winner = didPlayerWin();

        renderMessageOnCorrectAnswer();
        setNextPlayer();

        return winner;

    }

    private void setNextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == howManyPlayers())
            currentPlayerIndex = 0;
        currentPlayer = players.get(currentPlayerIndex);
    }

    private void renderMessageOnCorrectAnswer() {
        System.out.println("Answer was correct!!!!");
        System.out.println(currentPlayer.getName()
                + " now has "
                + currentPlayer.getPurse()
                + " Gold Coins.");
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);

        setNextPlayer();
        return true;
    }

    private boolean didPlayerWin() {
        return !(currentPlayer.getPurse() == 6);
    }


}
