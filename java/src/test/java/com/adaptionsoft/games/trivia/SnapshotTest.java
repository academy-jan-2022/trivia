package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnapshotTest {
    @Test
    void
    should_match_snapshot() throws IOException {
        Path path = Paths.get("src/test/snapshots/0/output.txt");
        var snapshot = String.join(System.lineSeparator(), Files.readAllLines(path));

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


        assertEquals(snapshot, output.toString().trim());

    }

    public record Play(int roll, boolean isCorrect) { }
}
