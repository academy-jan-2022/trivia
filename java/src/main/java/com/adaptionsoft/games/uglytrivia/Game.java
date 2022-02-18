package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList<String> players = new ArrayList<String>();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
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
        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            penaltyBoxTurn(roll);

        } else {
            regularTurn(roll);
        }

    }

    private void penaltyBoxTurn(int roll) {
        boolean shouldGetOutOfPenaltyBox = roll % 2 != 0;
        if (shouldGetOutOfPenaltyBox) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            regularTurn(roll);

        } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
        }
    }

    private void setPlayerPlace(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
    }

    private void regularTurn(int roll) {
        setPlayerPlace(roll);

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
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
        return places[currentPlayer] == 0 || places[currentPlayer] == 4 || places[currentPlayer] == 8;
    }

    private boolean isScienceCategory() {
        return places[currentPlayer] == 1 || places[currentPlayer] == 5 || places[currentPlayer] == 9;
    }

    private boolean isSportsCategory() {
        return places[currentPlayer] == 2 || places[currentPlayer] == 6 || places[currentPlayer] == 10;
    }

    public boolean wasCorrectlyAnswered() {
        if (isGettingOutOfPenaltyBox) {
            purses[currentPlayer]++;

            boolean winner = didPlayerWin();

            renderMessageOnCorrectAnswer();
            setNextPlayer();

            return winner;
        }

        if (inPenaltyBox[currentPlayer]) {
            setNextPlayer();
            return true;
        }

        purses[currentPlayer]++;
        boolean winner = didPlayerWin();

        renderMessageOnCorrectAnswer();
        setNextPlayer();

        return winner;

    }

    private void setNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private void renderMessageOnCorrectAnswer() {
        System.out.println("Answer was correct!!!!");
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        setNextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
