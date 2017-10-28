package mypackage;

import static org.junit.Assert.fail;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Roulette.class)
public class RouletteTest {

	private Roulette roulette;
	Map<String, Player> testPlayers;

	@Mock
	private Player testPlayerA;
	private Console mockConsole;
	@Captor
	private ArgumentCaptor<Bet> captorBet;

	private static final String PLAYER_A_NAME = "PlayerA";
	private static final String PLAYER_B_NAME = "PlayerB";
	private static final String BET_AMOUNT = "1.0";
	private static final String BET_ON_NUMBER = "2";
	private static final String CONSOLE_INPUT_MISSING_FIELD = PLAYER_A_NAME + " " + BET_ON_NUMBER;
	private static final String CONSOLE_INPUT_INVALID_PLAYER = "PlayerC" + " " + BET_ON_NUMBER + " " + BET_AMOUNT;
	private static final String CONSOLE_INPUT_SMALLER_BET_CHOICE = PLAYER_A_NAME + " 0 " + BET_AMOUNT;
	private static final String CONSOLE_INPUT_LARGER_BET_CHOICE = PLAYER_A_NAME + " 40 " + BET_AMOUNT;
	private static final String CONSOLE_INPUT_INVALID_WORD_BET_CHOICE = PLAYER_A_NAME + " ALL " + BET_AMOUNT;
	private static final String CONSOLE_INPUT_INVALID_BET_FORMAT = PLAYER_A_NAME + " 40 1";
	private static final String CONSOLE_INPUT_BET_ON_NUMBER = PLAYER_A_NAME + " " + BET_ON_NUMBER + " " + BET_AMOUNT;
	private static final String CONSOLE_INPUT_BET_ON_EVEN = PLAYER_A_NAME + " EVEN " + BET_AMOUNT;
	private static final String CONSOLE_INPUT_BET_ON_ODD = PLAYER_A_NAME + " ODD " + BET_AMOUNT;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.testPlayers = new HashMap<>();
		testPlayers.put(PLAYER_A_NAME, testPlayerA);
		Mockito.when(this.testPlayerA.getPlayerName()).thenReturn(PLAYER_A_NAME);
		this.roulette = new Roulette(testPlayers);

		PowerMockito.mockStatic(System.class);
		this.mockConsole = PowerMockito.mock(Console.class);
		Mockito.when(System.console()).thenReturn(mockConsole);
	}

	@Test
	public void testRoulette() {
		Assert.assertNotNull(this.roulette);
	}

	@Test
	public void testAddBetForPlayer() {
		Bet mockBet = Mockito.mock(Bet.class);
		this.roulette.addBetForPlayer(PLAYER_A_NAME, mockBet);
		Mockito.verify(this.testPlayerA).addBet(mockBet);
	}

	@Test
	public void testAddPlayer() {
		this.roulette.addPlayer(PLAYER_B_NAME);
		verifyPlayerAdded(PLAYER_B_NAME);
	}

	@Test
	public void testAcceptBet_InvalidConsoleInput() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_MISSING_FIELD);
		this.roulette.acceptBets();
		verifySystemErrorPrint();
	}

	@Test
	public void testAcceptBet_InvalidPlayer() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_INVALID_PLAYER);
		this.roulette.acceptBets();
		verifySystemErrorPrint();
	}

	@Test
	public void testAcceptBet_SmallerBetChoice() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_SMALLER_BET_CHOICE);
		this.roulette.acceptBets();
		verifySystemErrorPrint();
	}

	@Test
	public void testAcceptBet_LargerBetChoice() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_LARGER_BET_CHOICE);
		this.roulette.acceptBets();
		verifySystemErrorPrint();
	}

	@Test
	public void testAcceptBet_InvalidWordBetChoice() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_INVALID_WORD_BET_CHOICE);
		this.roulette.acceptBets();
		verifySystemErrorPrint();
	}

	@Test
	public void testAcceptBet_InvalidBetAmountFormat() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_INVALID_BET_FORMAT);
		this.roulette.acceptBets();
		verifySystemErrorPrint();
	}

	@Test
	public void testAcceptBet_BetOnNumber() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_BET_ON_NUMBER);
		this.roulette.acceptBets();
		verifyBetAdded(PLAYER_A_NAME, BET_ON_NUMBER, BET_AMOUNT);
	}

	@Test
	public void testAcceptBet_BetOnEven() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_BET_ON_EVEN);
		this.roulette.acceptBets();
		verifyBetAdded(PLAYER_A_NAME, "EVEN", BET_AMOUNT);
	}

	@Test
	public void testAcceptBet_BetOnOdd() throws InterruptedException {
		trainConsoleInput(CONSOLE_INPUT_BET_ON_ODD);
		this.roulette.acceptBets();
		verifyBetAdded(PLAYER_A_NAME, "ODD", BET_AMOUNT);
	}

	@Test
	public void testStartGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadPlayerInfo() {
		fail("Not yet implemented");
	}

	private void verifyPlayerAdded(String playerName) {
		Assert.assertEquals(2, this.testPlayers.size());
		Assert.assertNotNull(this.testPlayers.get(playerName));
	}

	private void trainConsoleInput(String input) {
		PowerMockito.doAnswer(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				RouletteTest.this.roulette.stopAcceptBets();
				return input;
			}
		}).when(mockConsole).readLine();
	}

	private void verifySystemErrorPrint() {
		PowerMockito.verifyStatic();
		System.err.println(Mockito.anyString());
	}

	private void verifyBetAdded(String playerName, String betChoice, String amount) {
		Mockito.verify(this.testPlayerA).addBet(this.captorBet.capture());
		Bet actual = this.captorBet.getValue();
		Assert.assertEquals(amount, String.valueOf(actual.getAmount()));
		Assert.assertEquals(betChoice, actual.getBetChoice());

	}

}
