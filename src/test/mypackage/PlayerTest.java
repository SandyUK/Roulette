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
		this.player = new Player(PLAYER_NAME);
	}

	@Test
	public void testPlayerString() {
		Assert.assertNotNull(this.player);
		Assert.assertEquals(PLAYER_NAME, this.player.getPlayerName());
		Assert.assertNotNull(this.player.getBets());
	}

	@Test
	public void testPlayerStringListOfBet() {
		List<Bet> bets = Arrays.asList(new Bet("1", 2), new Bet("2", 3));
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
		List<Bet> bets = Arrays.asList(new Bet("1", 2), new Bet("2", 3));
		this.player = new Player(PLAYER_NAME, bets);
		Assert.assertNotNull(this.player);
		Assert.assertEquals(bets, this.player.getBets());
	}

	@Test
	public void testSetBets() {
		Assert.assertTrue(this.player.getBets().isEmpty());
		List<Bet> bets = Arrays.asList(new Bet("1", 2), new Bet("2", 3));
		this.player.setBets(bets);
		Assert.assertEquals(bets, this.player.getBets());
	}

	@Test
	public void testAddBet() {
		Assert.assertTrue(this.player.getBets().isEmpty());
		Bet bet = new Bet("EVEN", 5);
		this.player.addBet(bet);
		Assert.assertEquals(1, this.player.getBets().size());
		Assert.assertEquals(bet, this.player.getBets().get(0));
	}

}
