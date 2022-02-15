using System;
using System.Collections.Generic;
using System.IO;
using Trivia;
using Xunit;

namespace Tests;

public class SnapshotTest
{
    private const string Snapshot =
@"Chet was added
They are player number 1
Pat was added
They are player number 2
Sue was added
They are player number 3
Chet is the current player
They have rolled a 3
Chet's new location is 3
The category is Rock
Rock Question 0
Answer was corrent!!!!        *
Chet now has 1 Gold Coins.
Pat is the current player
They have rolled a 3
Pat's new location is 3
The category is Rock
Rock Question 1
Answer was corrent!!!!        *
Pat now has 1 Gold Coins.
Sue is the current player
They have rolled a 3
Sue's new location is 3
The category is Rock
Rock Question 2
Answer was corrent!!!!        *
Sue now has 1 Gold Coins.
Chet is the current player
They have rolled a 3
Chet's new location is 6
The category is Sports
Sports Question 0
Answer was corrent!!!!        *
Chet now has 2 Gold Coins.
Pat is the current player
They have rolled a 4
Pat's new location is 7
The category is Rock
Rock Question 3
Answer was corrent!!!!        *
Pat now has 2 Gold Coins.
Sue is the current player
They have rolled a 2
Sue's new location is 5
The category is Science
Science Question 0
Answer was corrent!!!!        *
Sue now has 2 Gold Coins.
Chet is the current player
They have rolled a 4
Chet's new location is 10
The category is Sports
Sports Question 1
Answer was corrent!!!!        *
Chet now has 3 Gold Coins.
Pat is the current player
They have rolled a 5
Pat's new location is 0
The category is Pop
Pop Question 0
Answer was corrent!!!!        *
Pat now has 3 Gold Coins.
Sue is the current player
They have rolled a 4
Sue's new location is 9
The category is Science
Science Question 1
Answer was corrent!!!!
Sue now has 3 Gold Coins.
Chet is the current player
They have rolled a 5
Chet's new location is 3
The category is Rock
Rock Question 4
Answer was corrent!!!!
Chet now has 4 Gold Coins.
Pat is the current player
They have rolled a 1
Pat's new location is 1
The category is Science
Science Question 2
Answer was corrent!!!!
Pat now has 4 Gold Coins.
Sue is the current player
They have rolled a 5
Sue's new location is 2
The category is Sports
Sports Question 2
Answer was corrent!!!!
Sue now has 4 Gold Coins.
Chet is the current player
They have rolled a 2
Chet's new location is 5
The category is Science
Science Question 3
Question was incorrectly answered
Chet was sent to the penalty box
Pat is the current player
They have rolled a 3
Pat's new location is 4
The category is Pop
Pop Question 1
Answer was corrent!!!!
Pat now has 5 Gold Coins.
Sue is the current player
They have rolled a 5
Sue's new location is 7
The category is Rock
Rock Question 5
Answer was corrent!!!!
Sue now has 5 Gold Coins.
Chet is the current player
They have rolled a 2
Chet is not getting out of the penalty box
Pat is the current player
They have rolled a 4
Pat's new location is 8
The category is Pop
Pop Question 2
Answer was corrent!!!!
Pat now has 6 Gold Coins.
";

    [Fact(DisplayName = "produces the same output")]
    public void Test1()
    {
        using var stringWriter = new StringWriter();
        Console.SetOut(stringWriter);

        var aGame = new Game();
        aGame.Add("Chet");
        aGame.Add("Pat");
        aGame.Add("Sue");
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
        };

        foreach (var (roll, isCorrect) in plays)
        {
            aGame.Roll(roll);
            if (isCorrect)
                aGame.WasCorrectlyAnswered();
            else
                aGame.WrongAnswer();
        }
        Assert.Equal(Snapshot, stringWriter.ToString());
    }
}

public record Play(int Roll, bool IsCorrect);
