package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnapshotTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void
    should_match_snapshot(int index) throws IOException {
        Path snapshotPath = Paths.get("src/test/snapshots/"+ index + "/output.txt");
        var snapshot = String.join(System.lineSeparator(), Files.readAllLines(snapshotPath));

        Path inputPath = Paths.get("src/test/snapshots/" + index + "/input.csv");
        var inputs = Files.readAllLines(inputPath);

        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        var aGame = new Game();

        System.setOut(new PrintStream(output));

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        List<Play> plays = new java.util.ArrayList<>(List.of());

        for (String input: inputs) {
            var play = input.split(",");
            plays.add(
                    new Play(Integer.parseInt(play[0]), Boolean.parseBoolean(play[1]))
            );
        }

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
