using System;
using System.Collections.Generic;

namespace Trivia
{
    public class GameRunner
    {
        private static bool _notAWinner;

        public static void Main(string[] args)
        {
            var aGame = new Game();

            aGame.Add("Chet");
            aGame.Add("Pat");
            aGame.Add("Sue");

            var rand = new Random();

            var plays = new List<string>();

            do
            {
                var roll = rand.Next(5) + 1;
                aGame.Roll(roll);

                var isFail = rand.Next(2) == 1;
                if (isFail)
                {
                    _notAWinner = aGame.WrongAnswer();
                }
                else
                {
                    _notAWinner = aGame.WasCorrectlyAnswered();
                }
                plays.Add($"{roll},{!isFail}");
            } while (_notAWinner);

            foreach (var play in plays)
            {
                Console.WriteLine(play);
            }
        }
    }
}
