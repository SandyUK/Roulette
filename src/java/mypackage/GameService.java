package mypackage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import mypackage.Result.OUTCOME;

/**
 * The casino game Roulette.
 */
public class GameService {

	/**
	 * A map from player name to a {@link Player}.
	 */
	private Map<String, Player> players;

	/**
	 * A list of bet input data.
	 */
	private ConcurrentLinkedQueue<BetInputData> bets;

	/**
	 * The random number.
	 */
	private int randomNumber;

	/**
	 * The round number
	 */
	private int roundNumber = 1;

	/**
	 * Defines a scheduled single-threaded executor.
	 */
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	/**
	 * Constructor.
	 */
	public GameService() {
		this.players = new HashMap<String, Player>();
		this.bets = new ConcurrentLinkedQueue<BetInputData>();
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param name
	 *            the name of the player to add.
	 */
	public void addPlayer(String name, double totalWin, double totalBet) {
		this.players.put(name, new Player(name, totalWin, totalBet));
	}

	/**
	 * Starts the game.
	 * 
	 * @throws InterruptedException
	 *             thrown if the main thread is interrupted
	 */
	public void startGame() throws InterruptedException {
		System.out.println("Existing players:");
		this.players.keySet().stream().forEach(key -> System.out.println(key));
		// creates a task to generate a random number and prints out game
		// results every 30 seconds

		Runnable randomGenerator = () -> {
			// generate a random number
			Random random = new Random();
			this.randomNumber = random.nextInt(36) + 1;
			LocalDateTime now = LocalDateTime.now();
			// adds all the bets that were made before 'now' to the round and
			// remove them from the queue, leave others to the next round
			this.bets.stream().filter(bet -> bet.getBetTime().isBefore(now)).forEach(bet -> {
				this.bets.remove(bet);
				this.players.get(bet.getPlayerName()).getBets().add(new BetBasicData(bet.getBetChoice(), bet.getAmount()));
			});
			System.out.printf("The ball landed on number %s.\n", this.randomNumber);
			System.out.println("Round " + this.roundNumber++);
			// print out the game results
			this.printGameResult();
			// clear existing bets for players
			this.players.values().forEach(player -> player.getBets().clear());
		};
		this.executor.scheduleAtFixedRate(randomGenerator, 30, 30, TimeUnit.SECONDS);
	}

	/**
	 * Method that shuts down the scheduled executor which stops the game.
	 */
	public void stopGame() {
		this.executor.shutdown();
	}

	/**
	 * Adds a bet.
	 * 
	 * @param data
	 *            info about the bet
	 */
	public void addBets(BetInputData data) {
		this.bets.add(data);
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
		System.out.println("----Output 1-----");
		System.out.format("%-50s%10s%10s%10s\n", "Player", "Bet", "Outcome", "Winnings");
		for (Entry<String, Player> entry : this.players.entrySet()) {
			Player player = entry.getValue();
			for (BetBasicData bet : player.getBets()) {
				double winnings = 0.0;
				if (String.valueOf(this.randomNumber).equals(bet.getBetChoice())) {
					winnings = 36 * bet.getAmount();
				} else if ("EVEN".equals(bet.getBetChoice()) && this.randomNumber % 2 == 0) {
					winnings = 2 * bet.getAmount();
				} else if ("ODD".equals(bet.getBetChoice()) && this.randomNumber % 2 != 0) {
					winnings = 2 * bet.getAmount();
				}
				player.setTotalWin(player.getTotalWin() + winnings);
				player.setTotalBet(player.getTotalBet() + bet.getAmount());
				System.out.format("%-50s%10s%10s%.1f\n", entry.getKey(), bet.getBetChoice(), (winnings > 0 ? OUTCOME.WIN
						: OUTCOME.LOSE), winnings);
			}
		}
		System.out.println("----Output 2-----");
		System.out.format("%-50s%10s%10s\n", "Player", "Total Win", "Total Bet");
		for (Entry<String, Player> entry : this.players.entrySet()) {
			Player player = entry.getValue();
			System.out.format("%-50s%.1f%.1f\n", player.getPlayerName(), player.getTotalWin(), player.getTotalBet());
		}
	}
}
