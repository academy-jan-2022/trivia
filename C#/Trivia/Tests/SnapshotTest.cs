using System;
using System.Collections.Generic;
using System.IO;
using Trivia;
using Xunit;

namespace Tests;

public class SnapshotTest
{
    [Fact(DisplayName = "produces the same output")]
    public void Test1()
    {
        var snapshot =
            string.Join(
                "\r\n",
                File
                    .ReadAllLines("./Snapshots/0/output.txt")
            ) + "\r\n";

        using var stringWriter = new StringWriter();
        Console.SetOut(stringWriter);

        var aGame = new Game();
        aGame.Add("Chet");
        aGame.Add("Pat");
        aGame.Add("Sue");

        var playLines = File.ReadAllLines("./Snapshots/0/plays.csv");
        var plays = new List<Play>
        {
            new(3, true),
            new(3, true),
            new(3, true),
            new(3, true),
            new(4, true),
            new(2, true),
            new(4, true),
            new(5, true),
            new(4, true),
            new(5, true),
            new(1, true),
            new(5, true),
            new(2, false),
            new(3, true),
            new(5, true),
            new(2, true),
            new(4, true),
        };

        foreach (var (roll, isCorrect) in plays)
        {
            aGame.Roll(roll);
            if (isCorrect)
                aGame.WasCorrectlyAnswered();
            else
                aGame.WrongAnswer();
        }
        Assert.Equal(snapshot, stringWriter.ToString());
    }
}

public record Play(int Roll, bool IsCorrect);
