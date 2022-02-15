
package com.adaptionsoft.games.trivia.runner;
import java.util.ArrayList;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");
		
		Random rand = new Random();

		var plays = new ArrayList<Play>(){};

		do {
			var roll = rand.nextInt(5) + 1;
			var isIncorrect = rand.nextInt(9) == 7;

			var play = new Play(roll, !isIncorrect);

			plays.add(play);

			aGame.roll(roll);



			if (isIncorrect) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
			
			
			
		} while (notAWinner);

		for(Play play: plays){
			System.out.println(play.roll()+","+play.isCorrect());
		}
	}

	public record Play(int roll, boolean isCorrect) { }
}

