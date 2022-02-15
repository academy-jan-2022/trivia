using System;
using System.Collections.Generic;
using System.Linq;

namespace Trivia;

public class Game
{
    private readonly bool[] _inPenaltyBox = new bool[6];

    private readonly int[] _places = new int[6];
    private readonly List<string> _players = new();

    private readonly LinkedList<string> _popQuestions = new();
    private readonly int[] _purses = new int[6];
    private readonly LinkedList<string> _rockQuestions = new();
    private readonly LinkedList<string> _scienceQuestions = new();
    private readonly LinkedList<string> _sportsQuestions = new();

    private int _currentPlayer;
    private bool _isGettingOutOfPenaltyBox;

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

    public bool Add(string playerName)
    {
        _players.Add(playerName);
        _places[HowManyPlayers()] = 0;
        _purses[HowManyPlayers()] = 0;
        _inPenaltyBox[HowManyPlayers()] = false;

        Console.WriteLine($"{playerName} was added");
        Console.WriteLine($"They are player number {_players.Count}");
        return true;
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
            _places[_currentPlayer] = _places[_currentPlayer] + roll;
            if (_places[_currentPlayer] > 11)
                _places[_currentPlayer] = _places[_currentPlayer] - 12;

            Console.WriteLine($"{_players[_currentPlayer]}'s new location is {_places[_currentPlayer]}");
            Console.WriteLine($"The category is {CurrentCategory()}");
            AskQuestion();
        }

        void PenaltyBoxTurn()
        {
            if (roll % 2 != 0)
            {
                _isGettingOutOfPenaltyBox = true;

                Console.WriteLine($"{_players[_currentPlayer]} is getting out of the penalty box");
                _places[_currentPlayer] = _places[_currentPlayer] + roll;
                if (_places[_currentPlayer] > 11) _places[_currentPlayer] = _places[_currentPlayer] - 12;

                Console.WriteLine($"{_players[_currentPlayer]}'s new location is {_places[_currentPlayer]}");
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
        if (_places[_currentPlayer] == 0) return Category.Pop;
        if (_places[_currentPlayer] == 4) return Category.Pop;
        if (_places[_currentPlayer] == 8) return Category.Pop;
        if (_places[_currentPlayer] == 1) return Category.Science;
        if (_places[_currentPlayer] == 5) return Category.Science;
        if (_places[_currentPlayer] == 9) return Category.Science;
        if (_places[_currentPlayer] == 2) return Category.Sports;
        if (_places[_currentPlayer] == 6) return Category.Sports;
        if (_places[_currentPlayer] == 10) return Category.Sports;
        return Category.Rock;
    }

    public bool WasCorrectlyAnswered()
    {
        if (_inPenaltyBox[_currentPlayer])
        {
            if (_isGettingOutOfPenaltyBox)
            {
                Console.WriteLine("Answer was correct!!!!");
                _purses[_currentPlayer]++;
                Console.WriteLine($"{_players[_currentPlayer]} now has {_purses[_currentPlayer]} Gold Coins.");

                var winner = DidPlayerWin();
                _currentPlayer++;
                if (_currentPlayer == _players.Count) _currentPlayer = 0;

                return winner;
            }

            _currentPlayer++;
            if (_currentPlayer == _players.Count) _currentPlayer = 0;
            return true;
        }

        {
            Console.WriteLine("Answer was corrent!!!!");
            _purses[_currentPlayer]++;
            Console.WriteLine($"{_players[_currentPlayer]} now has {_purses[_currentPlayer]} Gold Coins.");

            var winner = DidPlayerWin();
            _currentPlayer++;
            if (_currentPlayer == _players.Count) _currentPlayer = 0;

            return winner;
        }
    }

    public bool WrongAnswer()
    {
        Console.WriteLine("Question was incorrectly answered");
        Console.WriteLine($"{_players[_currentPlayer]} was sent to the penalty box");
        _inPenaltyBox[_currentPlayer] = true;

        _currentPlayer++;
        if (_currentPlayer == _players.Count) _currentPlayer = 0;
        return true;
    }


    private bool DidPlayerWin() =>
        _purses[_currentPlayer] != 6;
}

public enum Category
{
    Pop,
    Science,
    Sports,
    Rock
}
