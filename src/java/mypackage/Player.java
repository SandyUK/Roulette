package mypackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
	/**
	 * Name of player
	 */
	private String playerName;

	/**
	 * How much the player has totally won.
	 */
	private double totalWin;

	/**
	 * How much the player has totally bet.
	 */
	private double totalBet;
	/**
	 * The bets the player has made.
	 */
	private List<BetBasicData> bets;

	/**
	 * Constructor with given player name and the total win, total bet values.
	 * 
	 * @param playerName
	 *            the name of the player
	 */
	public Player(String playerName, double totalWin, double totalBet) {
		this.playerName = playerName;
		this.totalWin = totalWin;
		this.totalBet = totalBet;
		this.bets = new ArrayList<>();
	}

	/**
	 * Constructor with given player name and the bets the player has made.
	 * 
	 * @param playerName
	 *            the name of the player
	 * @param bets
	 *            the bets the player has made
	 */
	public Player(String playerName, List<BetBasicData> bets) {
		this.playerName = playerName;
		this.bets = bets;
	}

	/**
	 * @return the player name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName
	 *            the player name to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the bet
	 */
	public List<BetBasicData> getBets() {
		return bets;
	}

	/**
	 * @param bet
	 *            the bet to set
	 */
	public void setBets(List<BetBasicData> bets) {
		this.bets = bets;
	}

	/**
	 * Adds a bet for the player.
	 * 
	 * @param bet
	 *            the bet to add
	 */
	public void addBet(BetBasicData bet) {
		this.bets.add(bet);
	}

	/**
	 * @return the totalWin
	 */
	public double getTotalWin() {
		return totalWin;
	}

	/**
	 * @param totalWin
	 *            the totalWin to set
	 */
	public void setTotalWin(double totalWin) {
		this.totalWin = totalWin;
	}

	/**
	 * @return the totalBet
	 */
	public double getTotalBet() {
		return totalBet;
	}

	/**
	 * @param totalBet
	 *            the totalBet to set
	 */
	public void setTotalBet(double totalBet) {
		this.totalBet = totalBet;
	}
}
