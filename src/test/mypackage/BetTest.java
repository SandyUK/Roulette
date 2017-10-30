package mypackage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BetTest {
	private BetBasicData bet;

	@Before
	public void setUp() {
		this.bet = new BetBasicData("1", 2);
	}

	@Test
	public void testBet() {
		Assert.assertNotNull(this.bet);
		Assert.assertEquals("1", this.bet.getBetChoice());
		Assert.assertTrue(2.0 == this.bet.getAmount());
	}

	@Test
	public void testGetBetChoice() {
		Assert.assertNotNull(this.bet);
		Assert.assertEquals("1", this.bet.getBetChoice());
	}

	@Test
	public void testSetBetChoice() {
		Assert.assertEquals("1", this.bet.getBetChoice());
		this.bet.setBetChoice("EVEN");
		Assert.assertEquals("EVEN", this.bet.getBetChoice());
	}

	@Test
	public void testGetAmount() {
		Assert.assertNotNull(this.bet);
		Assert.assertTrue(2.0 == this.bet.getAmount());
	}

	@Test
	public void testSetAmount() {
		Assert.assertNotNull(this.bet);
		Assert.assertTrue(2.0 == this.bet.getAmount());
		this.bet.setAmount(3.0);
		Assert.assertTrue(3.0 == this.bet.getAmount());
	}

}
