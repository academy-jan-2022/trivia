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
        using var stringWriter = new StringWriter();
        Console.SetOut(stringWriter);

        var aGame = new Game();
        aGame.Add("Chet");
        aGame.Add("Pat");
        aGame.Add("Sue");

        var (snapshot, plays) = GetGame(0);

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

    private (string snapshot, List<Play> plays) GetGame(int index)
    {
        var snapshotFileName = $"./Snapshots/{index}/output.txt";
        var playsFileName = $"./Snapshots/{index}/plays.csv";
        var snapshot =
            string.Join(
                "\r\n",
                File
                    .ReadAllLines(snapshotFileName)
            ) + "\r\n";
        var playLines = File.ReadAllLines(playsFileName);
        var plays = new List<Play>();
        foreach (var playLine in playLines)
        {
            var parts = playLine.Split(",");
            plays.Add(new Play(int.Parse(parts[0]), bool.Parse(parts[1])));
        }

        return (snapshot, plays);
    }
}

public record Play(int Roll, bool IsCorrect);
