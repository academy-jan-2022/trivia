using System;
using System.Collections.Generic;
using System.Linq;

namespace Trivia;

public class Player
{
    public Player(string name) =>
        Name = name;

    public string Name { get; }
    public PenaltyBoxState PenaltyBoxState { get; set; }
    public int Purse { get; set; }
    public int Place { get; set; }
}

public enum PenaltyBoxState
{
    In = 1,
    Out = 0,
    Leaving = 2
}

public class Game
{
    private readonly bool[] _inPenaltyBox = new bool[6];
    private bool _isGettingOutOfPenaltyBox;
    private int _currentPlayer;

    private Player CurrentPlayer => players[_currentPlayer];

    private readonly List<Player> players = new();
    private readonly List<string> _players = new();

    private readonly LinkedList<string> _popQuestions = new();
    private readonly LinkedList<string> _rockQuestions = new();
    private readonly LinkedList<string> _scienceQuestions = new();
    private readonly LinkedList<string> _sportsQuestions = new();


    public Game()
    {
        for (var i = 0; i < 50; i++)
        {
            _popQuestions.AddLast(CreateQuestion(Category.Pop, i));
            _scienceQuestions.AddLast(CreateQuestion(Category.Science, i));
            _sportsQuestions.AddLast(CreateQuestion(Category.Sports, i));
            _rockQuestions.AddLast(CreateQuestion(Category.Rock, i));
        }
    }

    public string CreateQuestion(Category category, int index) =>
        $"{category} Question {index}";

    public bool IsPlayable() =>
        HowManyPlayers() >= 2;

    public void Add(string playerName)
    {
        players.Add(new Player(playerName));
        _players.Add(playerName);
        _inPenaltyBox[HowManyPlayers()] = false;

        Console.WriteLine($"{playerName} was added");
        Console.WriteLine($"They are player number {players.Count}");
    }

    public int HowManyPlayers() =>
        _players.Count;

    public void Roll(int roll)
    {
        Console.WriteLine($"{_players[_currentPlayer]} is the current player");
        Console.WriteLine($"They have rolled a {roll}");

        if (_inPenaltyBox[_currentPlayer])
            PenaltyBoxTurn();
        else
            NormalTurn();

        void NormalTurn()
        {
            SetNextPosition(roll);

            Console.WriteLine($"{_players[_currentPlayer]}'s new location is {CurrentPlayer.Place}");
            Console.WriteLine($"The category is {CurrentCategory()}");
            AskQuestion();
        }

        void PenaltyBoxTurn()
        {
            if (roll % 2 != 0)
            {
                _isGettingOutOfPenaltyBox = true;

                Console.WriteLine($"{_players[_currentPlayer]} is getting out of the penalty box");
                
                SetNextPosition(roll);

                Console.WriteLine($"{_players[_currentPlayer]}'s new location is {CurrentPlayer.Place}");
                Console.WriteLine($"The category is {CurrentCategory()}");
                AskQuestion();
            }
            else
            {
                Console.WriteLine($"{_players[_currentPlayer]} is not getting out of the penalty box");
                _isGettingOutOfPenaltyBox = false;
            }
        }
    }

    private void SetNextPosition(int roll)
    {
        var sum = CurrentPlayer.Place + roll;
        CurrentPlayer.Place = sum > 11 ? sum - 12 : sum;
    }

    private void AskQuestion()
    {
        if (CurrentCategory() == Category.Pop)
        {
            Console.WriteLine(_popQuestions.First());
            _popQuestions.RemoveFirst();
        }

        if (CurrentCategory() == Category.Science)
        {
            Console.WriteLine(_scienceQuestions.First());
            _scienceQuestions.RemoveFirst();
        }

        if (CurrentCategory() == Category.Sports)
        {
            Console.WriteLine(_sportsQuestions.First());
            _sportsQuestions.RemoveFirst();
        }

        if (CurrentCategory() == Category.Rock)
        {
            Console.WriteLine(_rockQuestions.First());
            _rockQuestions.RemoveFirst();
        }
    }

    private Category CurrentCategory()
    {
        if (CurrentPlayer.Place is 0 or 4 or 8)
            return Category.Pop;
        if (CurrentPlayer.Place is 1 or 5 or 9)
            return Category.Science;
        if (CurrentPlayer.Place is 2 or 6 or 10)
            return Category.Sports;
        return Category.Rock;
    }

    public bool GiveCorrectAnswer()
    {
        if (_inPenaltyBox[_currentPlayer])
        {
            if (_isGettingOutOfPenaltyBox)
            {
                Console.WriteLine("Answer was correct!!!!");
                CurrentPlayer.Purse++;
                Console.WriteLine($"{_players[_currentPlayer]} now has {CurrentPlayer.Purse} Gold Coins.");

                var winner = DidPlayerWin();
                SetNextPlayer();

                return winner;
            }

            SetNextPlayer();
            return true;
        }

        {
            Console.WriteLine("Answer was corrent!!!!");
            CurrentPlayer.Purse++;
            Console.WriteLine($"{_players[_currentPlayer]} now has {CurrentPlayer.Purse} Gold Coins.");

            var winner = DidPlayerWin();
            SetNextPlayer();

            return winner;
        }
    }

    private void SetNextPlayer() =>
        _currentPlayer = (_currentPlayer + 1) % _players.Count;

    public bool GiveWrongAnswer()
    {
        Console.WriteLine("Question was incorrectly answered");
        Console.WriteLine($"{_players[_currentPlayer]} was sent to the penalty box");
        _inPenaltyBox[_currentPlayer] = true;

        SetNextPlayer();
        return true;
    }


    private bool DidPlayerWin() =>
        CurrentPlayer.Purse != 6;
}

public enum Category
{
    Pop,
    Science,
    Sports,
    Rock
}
