package mypackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * The casino game Roulette.
 */
public class Roulette {
	/**
	 * The enum type that represents a player has won or lost.
	 */
	private enum OUTCOME {
		WIN, LOSE
	}

	/**
	 * A map from player name to a {@link Player}.
	 */
	private Map<String, Player> players;

	/**
	 * The random number.
	 */
	private int randomNumber;

	/**
	 * The round number
	 */
	private int roundNumber = 1;

	/**
	 * Indicates whether the current round has ended.
	 */
	private boolean endOfRound;

	/**
	 * A flag to stop accept bets.
	 */
	private boolean acceptBets;

	/**
	 * Constructor.
	 */
	public Roulette(Map<String, Player> players) {
		if (players == null) {
			this.players = new HashMap<>();
		} else {
			this.players = players;
		}
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param name
	 *            the name of the player to add.
	 */
	public void addPlayer(String name) {
		this.players.put(name, new Player(name));
	}

	/**
	 * Adds a bet for an existing player.
	 * 
	 * @param name
	 *            the player name
	 * @param bet
	 *            the {@link Bet} to add
	 */
	public void addBetForPlayer(String name, Bet bet) {
		this.players.get(name).addBet(bet);
	}

	/**
	 * Starts the game.
	 * 
	 * @throws NumberFormatException
	 *             thrown if the third input from console is not a valid double
	 * @throws InterruptedException
	 *             thrown if the main thread is interrupted
	 */
	public void startGame() throws NumberFormatException, InterruptedException {
		// creates a task to generate a random number and prints out game
		// results every 30 seconds
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable randomGenerator = () -> {
			// generate a random number
			Random random = new Random();
			this.randomNumber = random.nextInt(36) + 1;
			this.endOfRound = true;
			System.out.printf("The ball landed on number %s.\n", this.randomNumber);
			System.out.println("Round " + this.roundNumber++);
			// print out the game results
			this.printGameResult();
			this.clearBets();
			this.endOfRound = false;
			Thread.currentThread().notify();
		};
		executor.scheduleAtFixedRate(randomGenerator, 30, 30, TimeUnit.SECONDS);
		acceptBets();
	}

	public boolean isEndOfRound() {
		return this.endOfRound;
	}

	public void acceptBets() throws InterruptedException {
		// the game accepts the bets from players all the time
		acceptBets = true;
		while (acceptBets) {
			if (this.endOfRound) {
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

				if (!isValidPlayer(playerName) || !this.isValidBetChoice(betChoice)) {
					continue;
				}

				double amount = Double.valueOf(infos[2]);
				this.addBetForPlayer(infos[0], new Bet(infos[1], amount));
			}
		}
	}

	public void stopAcceptBets() {
		this.acceptBets = false;
	}

	/**
	 * Reads player info from the given file.
	 * 
	 * @param path
	 *            the file path
	 * @throws IOException
	 *             thrown if an I/O error occurs opening the file
	 */
	public void readPlayerInfoFromFile(String path) throws IOException {
		// read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			stream.forEach(line -> this.addPlayer(line));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		this.players.keySet().stream().forEach(key -> System.out.println(key));
	}

	/**
	 * Verify the player is valid.
	 * 
	 * @param playerName
	 *            the player's name
	 * @return true if the player's name has been added to the game; false other
	 *         wise.
	 */
	public boolean isValidPlayer(String playerName) {
		boolean result = true;
		if (!this.players.containsKey(playerName)) {
			System.err.println("Player with name '" + playerName + "' has not been added to the game.");
			result = false;
		}
		return result;
	}

	/**
	 * Checks whether the given bet is valid.
	 * 
	 * @param choice
	 *            the bet choice to check
	 * @return true if valid, false otherwise
	 */
	public boolean isValidBetChoice(String choice) {
		try {
			if ("EVEN".equals(choice) || "ODD".equals(choice)) {
				return true;
			} else {
				int number = Integer.valueOf(choice);
				if (number > 0 && number <= 36) {
					return true;
				}
			}
		} catch (NumberFormatException ex) {
			System.err.println("Bet was placed in an invalid format.");
		}
		System.err.println("Bet on either a number from 1-36, EVEN or ODD.");
		return false;
	}

	/**
	 * Prints out the game result to console.
	 */
	private void printGameResult() {
		System.out.format("%-50s%10s%10s%10s\n", "Player", "Bet", "Outcome", "Winnings");
		for (Entry<String, Player> entry : this.players.entrySet()) {
			Player player = entry.getValue();
			for (Bet bet : player.getBets()) {
				double winnings = 0.0;
				if (String.valueOf(this.randomNumber).equals(bet.getBetChoice())) {
					winnings = 36 * bet.getAmount();
				} else if ("EVEN".equals(bet.getBetChoice()) && this.randomNumber % 2 == 0) {
					winnings = 2 * bet.getAmount();
				} else if ("ODD".equals(bet.getBetChoice()) && this.randomNumber % 2 != 0) {
					winnings = 2 * bet.getAmount();
				}
				System.out.format("%-50s%10s%10s%.1f\n", entry.getKey(), bet.getBetChoice(), (winnings > 0 ? OUTCOME.WIN
						: OUTCOME.LOSE), winnings);
			}
		}
	}

	/**
	 * Clears bets of players after each round.
	 */
	private void clearBets() {
		this.players.values().stream().forEach(player -> player.setBets(new ArrayList<Bet>()));
	}
}
