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
	 * The bets the player has made.
	 */
	private List<Bet> bets;

	/**
	 * Constructor with given player name.
	 * 
	 * @param playerName
	 *            the name of the player
	 */
	public Player(String playerName) {
		this.playerName = playerName;
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
	public Player(String playerName, List<Bet> bets) {
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
	public List<Bet> getBets() {
		return bets;
	}

	/**
	 * @param bet
	 *            the bet to set
	 */
	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	/**
	 * Adds a bet for the player.
	 * @param bet the bet to add
	 */
	public void addBet(Bet bet) {
		this.bets.add(bet);
	}
}
