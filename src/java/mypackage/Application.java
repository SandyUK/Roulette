package mypackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Game controller that receives bets from the console and starts the game.
 */
public class Application {
	/**
	 * Entry point of the application with one param for the file that contains
	 * player info.
	 * 
	 * @param args
	 *            the first element of which is the file containing player info
	 * @throws InterruptedException
	 *             thrown from startGame method
	 * @throws IOException
	 *             thrown if an I/O error occurs opening the file
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		GameService game = new GameService();
		// read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
			stream.forEach(line -> {
				String[] infos = line.split(",");
				if (infos.length == 3) {
					game.addPlayer(infos[0], Double.valueOf(infos[1]), Double.valueOf(infos[2]));
				} else if (infos.length == 2) {
					game.addPlayer(infos[0], Double.valueOf(infos[1]), 0);
				} else {
					game.addPlayer(infos[0], 0, 0);
				}
			});
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		}

		game.startGame();
		while (true) {
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
				game.addBets(new BetInputData(playerName, betChoice, amount, LocalDateTime.now()));
			}
		}
	}
}
