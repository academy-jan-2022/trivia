package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList<Player> players = new ArrayList<>();

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    int currentPlayerIndex = 0;
    private Player currentPlayer;

    enum Category {
        Pop,
        Science,
        Sports,
        Rock
    }
    
    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast(createQuestion(Category.Pop,  i));
            scienceQuestions.addLast(createQuestion(Category.Science,  i));
            sportsQuestions.addLast(createQuestion(Category.Sports,  i));
            rockQuestions.addLast(createQuestion(Category.Rock,  i));
        }
    }

    public String createQuestion(Category questionType, int index) {
        return questionType + " Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        if (currentPlayer == null) {
            currentPlayer = players.get(0);
        }

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
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

        System.out.println(currentPlayer.getName()
                + "'s new location is "
                + currentPlayer.getPlace());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory().equals(Category.Pop))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory().equals(Category.Science))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory().equals(Category.Sports))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory().equals(Category.Rock))
            System.out.println(rockQuestions.removeFirst());
    }

    private Category currentCategory() {
        if (isPopCategory()){
            return Category.Pop;
        }

        if (isScienceCategory()){
            return Category.Science;
        }

        if (isSportsCategory()){
            return Category.Sports;
        }

        return Category.Rock;
    }

    private boolean isPopCategory() {
        int currentPosition = currentPlayer.getPlace();
        return currentPosition == 0 || currentPosition == 4 || currentPosition == 8;
    }

    private boolean isScienceCategory() {
        int currentPosition = currentPlayer.getPlace();
        return currentPosition == 1 || currentPosition == 5 || currentPosition == 9;
    }

    private boolean isSportsCategory() {
        int currentPosition = currentPlayer.getPlace();
        return currentPosition == 2 || currentPosition == 6 || currentPosition == 10;
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
        if (currentPlayerIndex == players.size())
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
