package mypackage;

/**
 * Represents a bet from a player.
 */
public class Bet {
	/**
	 * Either a number between 1 and 36, EVEN or ODD
	 */
	private String betChoice;
	/**
	 * How much the player wants to bet
	 */
	private double amount;

	/**
	 * Constructor with the bet choice and multiple.
	 * 
	 * @param betChoice
	 *            the belt choice
	 * @param amount
	 *            how much the player wants to bet
	 */
	public Bet(String betChoice, double amount) {
		this.betChoice = betChoice;
		this.amount = amount;
	}

	/**
	 * @return the bet choice
	 */
	public String getBetChoice() {
		return betChoice;
	}

	/**
	 * @param betChoice
	 *            the bet choice to set
	 */
	public void setBetChoice(String betChoice) {
		this.betChoice = betChoice;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
