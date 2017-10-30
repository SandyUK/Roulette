package mypackage;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BetInputDataTest {
	private static final String PLAYER_NAME = "player";
	private static final LocalDateTime NOW = LocalDateTime.now();
	private BetInputData bet;

	@Before
	public void setUp() {
		this.bet = new BetInputData(PLAYER_NAME, "EVEN", 3.0, NOW);
	}

	@Test
	public void testBetInputData() {
		Assert.assertNotNull(this.bet);
		Assert.assertEquals(PLAYER_NAME, this.bet.getPlayerName());
		Assert.assertEquals("EVEN", this.bet.getBetChoice());
		Assert.assertTrue(3.0 == this.bet.getAmount());
		Assert.assertEquals(NOW, this.bet.getBetTime());
	}

	@Test
	public void testGetPlayerName() {
		Assert.assertNotNull(this.bet);
		Assert.assertEquals(PLAYER_NAME, this.bet.getPlayerName());
	}

	@Test
	public void testSetPlayerName() {
		Assert.assertEquals(PLAYER_NAME, this.bet.getPlayerName());
		this.bet.setPlayerName(PLAYER_NAME + 1);
		Assert.assertEquals(PLAYER_NAME + 1, this.bet.getPlayerName());
	}

	@Test
	public void testGetBetChoice() {
		Assert.assertNotNull(this.bet);
		Assert.assertEquals("EVEN", this.bet.getBetChoice());
	}

	@Test
	public void testSetBetChoice() {
		Assert.assertEquals("EVEN", this.bet.getBetChoice());
		this.bet.setBetChoice("ODD");
		Assert.assertEquals("ODD", this.bet.getBetChoice());
	}

	@Test
	public void testGetAmount() {
		Assert.assertNotNull(this.bet);
		Assert.assertTrue(3.0 == this.bet.getAmount());
	}

	@Test
	public void testSetAmount() {
		Assert.assertTrue(3.0 == this.bet.getAmount());
		this.bet.setAmount(5.0);
		Assert.assertTrue(5.0 == this.bet.getAmount());
	}

	@Test
	public void testGetBetTime() {
		Assert.assertNotNull(this.bet);
		Assert.assertEquals(NOW, this.bet.getBetTime());
	}

	@Test
	public void testSetBetTime() {
		Assert.assertEquals(NOW, this.bet.getBetTime());
		LocalDateTime newTime = LocalDateTime.now();
		this.bet.setBetTime(newTime);
		Assert.assertEquals(newTime, this.bet.getBetTime());
	}

}
