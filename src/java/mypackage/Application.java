package mypackage;

import java.io.IOException;
import java.util.HashMap;

public class Application {
	/**
	 * Entry point of the application with one param for the file that contains
	 * player info.
	 * 
	 * @param args
	 *            the first element of which is the file containing player info
	 * @throws NumberFormatException
	 *             thrown from startGame method
	 * @throws InterruptedException
	 *             thrown from startGame method
	 * @throws Exception
	 *             thrown from startGame method
	 */
	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		Roulette game = new Roulette(new HashMap<String, Player>());
		game.readPlayerInfoFromFile(args[0]);
		game.startGame();
		while (true) {
			if (game.isEndOfRound()) {
				Thread.currentThread().wait();
			}
			String[] infos = System.console().readLine().split(" ");
			if (infos.length < 3) {
				System.err.println(
						"Each line should contain the player's name, what they want to bet on (either a number from 1-36, EVEN or ODD), and how much they want to bet.");
				continue;
			} else {
				String playerName = infos[0];
				String betChoice = infos[1];

				if (!game.isValidPlayer(playerName) || !game.isValidBetChoice(betChoice)) {
					continue;
				}

				double amount = Double.valueOf(infos[2]);
				game.addBetForPlayer(infos[0], new Bet(infos[1], amount));
			}
		}
	}
}
