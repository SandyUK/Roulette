package mypackage;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private static final String PLAYER_NAME = "Tom";
	private Player player;

	@Before
	public void setUp() {
		this.player = new Player(PLAYER_NAME, 2.0, 3.0);
	}

	@Test
	public void testPlayerString() {
		Assert.assertNotNull(this.player);
		Assert.assertEquals(PLAYER_NAME, this.player.getPlayerName());
		Assert.assertTrue(2.0 == this.player.getTotalWin());
		Assert.assertTrue(3.0 == this.player.getTotalBet());
		Assert.assertNotNull(this.player.getBets());
	}

	@Test
	public void testPlayerStringListOfBet() {
		List<BetBasicData> bets = Arrays.asList(new BetBasicData("1", 2), new BetBasicData("2", 3));
		this.player = new Player(PLAYER_NAME, bets);
		Assert.assertNotNull(this.player);
		Assert.assertEquals(PLAYER_NAME, this.player.getPlayerName());
		Assert.assertEquals(bets, this.player.getBets());
	}

	@Test
	public void testGetPlayerName() {
		Assert.assertEquals(PLAYER_NAME, this.player.getPlayerName());
	}

	@Test
	public void testSetPlayerName() {
		Assert.assertEquals(PLAYER_NAME, this.player.getPlayerName());
		this.player.setPlayerName("Sam");
		Assert.assertEquals("Sam", this.player.getPlayerName());
	}

	@Test
	public void testGetBets() {
		List<BetBasicData> bets = Arrays.asList(new BetBasicData("1", 2), new BetBasicData("2", 3));
		this.player = new Player(PLAYER_NAME, bets);
		Assert.assertNotNull(this.player);
		Assert.assertEquals(bets, this.player.getBets());
	}

	@Test
	public void testSetBets() {
		Assert.assertTrue(this.player.getBets().isEmpty());
		List<BetBasicData> bets = Arrays.asList(new BetBasicData("1", 2), new BetBasicData("2", 3));
		this.player.setBets(bets);
		Assert.assertEquals(bets, this.player.getBets());
	}

	@Test
	public void testAddBet() {
		Assert.assertTrue(this.player.getBets().isEmpty());
		BetBasicData bet = new BetBasicData("EVEN", 5);
		this.player.addBet(bet);
		Assert.assertEquals(1, this.player.getBets().size());
		Assert.assertEquals(bet, this.player.getBets().get(0));
	}

	@Test
	public void testGetTotalWin() {
		Assert.assertTrue(2.0 == this.player.getTotalWin());
	}

	@Test
	public void testSetTotalWin() {
		Assert.assertTrue(2.0 == this.player.getTotalWin());
		this.player.setTotalWin(5.0);
		Assert.assertTrue(5.0 == this.player.getTotalWin());
	}

	@Test
	public void testGetTotalBet() {
		Assert.assertTrue(3.0 == this.player.getTotalBet());
	}

	@Test
	public void testSetTotalBet() {
		Assert.assertTrue(3.0 == this.player.getTotalBet());
		this.player.setTotalBet(6.0);
		Assert.assertTrue(6.0 == this.player.getTotalBet());
	}
}
