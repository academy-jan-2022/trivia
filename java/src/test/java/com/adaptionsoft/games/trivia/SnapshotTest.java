package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnapshotTest {

    private String snapshot = """
Chet was added
They are player number 1
Pat was added
They are player number 2
Sue was added
They are player number 3
Chet is the current player
They have rolled a 5
Chet's new location is 5
The category is Science
Science Question 0
Answer was corrent!!!!
Chet now has 1 Gold Coins.
Pat is the current player
They have rolled a 5
Pat's new location is 5
The category is Science
Science Question 1
Answer was corrent!!!!
Pat now has 1 Gold Coins.
Sue is the current player
They have rolled a 1
Sue's new location is 1
The category is Science
Science Question 2
Answer was corrent!!!!
Sue now has 1 Gold Coins.
Chet is the current player
They have rolled a 1
Chet's new location is 6
The category is Sports
Sports Question 0
Answer was corrent!!!!
Chet now has 2 Gold Coins.
Pat is the current player
They have rolled a 4
Pat's new location is 9
The category is Science
Science Question 3
Answer was corrent!!!!
Pat now has 2 Gold Coins.
Sue is the current player
They have rolled a 3
Sue's new location is 4
The category is Pop
Pop Question 0
Answer was corrent!!!!
Sue now has 2 Gold Coins.
Chet is the current player
They have rolled a 1
Chet's new location is 7
The category is Rock
Rock Question 0
Answer was corrent!!!!
Chet now has 3 Gold Coins.
Pat is the current player
They have rolled a 5
Pat's new location is 2
The category is Sports
Sports Question 1
Answer was corrent!!!!
Pat now has 3 Gold Coins.
Sue is the current player
They have rolled a 3
Sue's new location is 7
The category is Rock
Rock Question 1
Question was incorrectly answered
Sue was sent to the penalty box
Chet is the current player
They have rolled a 3
Chet's new location is 10
The category is Sports
Sports Question 2
Answer was corrent!!!!
Chet now has 4 Gold Coins.
Pat is the current player
They have rolled a 4
Pat's new location is 6
The category is Sports
Sports Question 3
Answer was corrent!!!!
Pat now has 4 Gold Coins.
Sue is the current player
They have rolled a 2
Sue is not getting out of the penalty box
Chet is the current player
They have rolled a 4
Chet's new location is 2
The category is Sports
Sports Question 4
Answer was corrent!!!!
Chet now has 5 Gold Coins.
Pat is the current player
They have rolled a 2
Pat's new location is 8
The category is Pop
Pop Question 1
Answer was corrent!!!!
Pat now has 5 Gold Coins.
Sue is the current player
They have rolled a 4
Sue is not getting out of the penalty box
Chet is the current player
They have rolled a 2
Chet's new location is 4
The category is Pop
Pop Question 2
Answer was corrent!!!!
Chet now has 6 Gold Coins.
            """;

    @Test
    void
    should_match_snapshot() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        var aGame = new Game();

        System.setOut(new PrintStream(output));

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");


        List<Play> plays = List.of(
                new Play(5,true),
                new Play(5,true),
                new Play(1,true),
                new Play(1,true),
                new Play(4,true),
                new Play(3,true),
                new Play(1,true),
                new Play(5,true),
                new Play(3,false),
                new Play(3,true),
                new Play(4,true),
                new Play(2,true),
                new Play(4,true),
                new Play(2,true),
                new Play(4,true),
                new Play(2,true)
        );

        for (Play play: plays) {
            aGame.roll(play.roll());



            if(play.isCorrect){
                aGame.wasCorrectlyAnswered();
            }else{
                aGame.wrongAnswer();
            }
        }



        assertEquals(snapshot, output.toString());

    }

    public record Play(int roll, boolean isCorrect) { }
}
