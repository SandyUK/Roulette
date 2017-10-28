package mypackage;

public class Result {
	/**
	 * The enum type that represents a player has won or lost.
	 */
	public enum OUTCOME {
		WIN, LOSE
	}

	private String playerName;
	private String betChoice;
	private OUTCOME outcome;
	private double winnings;

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the outcome
	 */
	public OUTCOME getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome
	 *            the outcome to set
	 */
	public void setOutcome(OUTCOME outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the winnings
	 */
	public double getWinnings() {
		return winnings;
	}

	/**
	 * @param winnings
	 *            the winnings to set
	 */
	public void setWinnings(double winnings) {
		this.winnings = winnings;
	}

	/**
	 * @return the betChoice
	 */
	public String getBetChoice() {
		return betChoice;
	}

	/**
	 * @param betChoice
	 *            the betChoice to set
	 */
	public void setBetChoice(String betChoice) {
		this.betChoice = betChoice;
	}
}
