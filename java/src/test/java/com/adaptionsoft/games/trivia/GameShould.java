package com.adaptionsoft.games.trivia;


import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameShould {

    @Test
    void release_player_from_penalty_and_play_next_turn() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        Game aGame = new Game();
        aGame.add("Chet");
        aGame.add("Pat");

        //chet
        aGame.roll(2);
        aGame.wrongAnswer();

        //pat
        aGame.roll(3);
        aGame.wasCorrectlyAnswered();

        System.setOut(new PrintStream(output));
        //chet
        aGame.roll(5);
        aGame.wasCorrectlyAnswered();

        //pat
        aGame.roll(3);
        aGame.wasCorrectlyAnswered();

        //chet
        aGame.roll(2);
        aGame.wasCorrectlyAnswered();

        String expected = """
                Chet is the current player
                They have rolled a 5
                Chet is getting out of the penalty box
                Chet's new location is 7
                The category is Rock
                Rock Question 1
                Answer was correct!!!!
                Chet now has 1 Gold Coins.
                Pat is the current player
                They have rolled a 3
                Pat's new location is 6
                The category is Sports
                Sports Question 1
                Answer was correct!!!!
                Pat now has 2 Gold Coins.
                Chet is the current player
                They have rolled a 2
                Chet's new location is 9
                The category is Science
                Science Question 0
                Answer was correct!!!!
                Chet now has 2 Gold Coins.
                """;

        assertEquals(
                expected.trim(), output.toString().replace("\r", "").trim());
    }
}
