package mypackage;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class GameServiceTest {
	private static final String PLAYER_NAME = "player";
	private GameService service;
	@Captor
	private ArgumentCaptor<Runnable> runnable;

	@Before
	public void setUp() {
		this.service = new GameService();
	}

	@Test
	public void testGameService() {
		Assert.assertNotNull(this.service);
		Assert.assertNotNull(Whitebox.getInternalState(this.service, Map.class));
		Assert.assertNotNull(Whitebox.getInternalState(this.service, ConcurrentLinkedQueue.class));
	}

	@Test
	public void testAddPlayer() {
		Assert.assertTrue(Whitebox.getInternalState(this.service, Map.class).isEmpty());
		this.service.addPlayer(PLAYER_NAME, 2.0, 1.0);
		Assert.assertEquals(1, Whitebox.getInternalState(this.service, Map.class).size());
		Assert.assertEquals(PLAYER_NAME, ((Player) Whitebox.getInternalState(this.service, Map.class).get(PLAYER_NAME))
				.getPlayerName());
	}

	@Test
	public void testStartGame() throws InterruptedException {
		this.service.addPlayer(PLAYER_NAME + 1, 2.0, 1.0);
		this.service.addPlayer(PLAYER_NAME + 2, 1.0, 2.0);
		this.service.addPlayer(PLAYER_NAME + 3, 3.0, 4.0);
		ScheduledExecutorService mockedExecutor = PowerMockito.mock(ScheduledExecutorService.class);
		Whitebox.setInternalState(this.service, ScheduledExecutorService.class, mockedExecutor);
		this.service.startGame();
		this.service.addBets(new BetInputData(PLAYER_NAME + 1, "EVEN", 2.0, LocalDateTime.now()));
		this.service.addBets(new BetInputData(PLAYER_NAME + 2, "ODD", 2.0, LocalDateTime.now()));
		this.service.addBets(new BetInputData(PLAYER_NAME + 3, "10", 2.0, LocalDateTime.now()));
		Assert.assertFalse(Whitebox.getInternalState(this.service, ScheduledExecutorService.class).isShutdown());
		Mockito.verify(mockedExecutor).scheduleAtFixedRate(this.runnable.capture(), Mockito.eq(30L), Mockito.eq(30L),
				Mockito.eq(TimeUnit.SECONDS));
		new Thread(this.runnable.getValue()).start();
		Thread.sleep(3000);
		this.service.stopGame();
	}

	@Test
	public void testStopGame() throws InterruptedException {
		this.service.startGame();
		Assert.assertFalse(Whitebox.getInternalState(this.service, ScheduledExecutorService.class).isShutdown());
		this.service.stopGame();
		Assert.assertTrue(Whitebox.getInternalState(this.service, ScheduledExecutorService.class).isShutdown());
	}

	@Test
	public void testAddBets() {
		Assert.assertTrue(Whitebox.getInternalState(this.service, ConcurrentLinkedQueue.class).isEmpty());
		BetInputData bet = new BetInputData(PLAYER_NAME, "EVEN", 2.0, LocalDateTime.now());
		this.service.addBets(bet);
		Assert.assertEquals(1, Whitebox.getInternalState(this.service, ConcurrentLinkedQueue.class).size());
		Assert.assertEquals(bet, Whitebox.getInternalState(this.service, ConcurrentLinkedQueue.class).peek());
	}

	@Test
	public void testIsValidPlayer_True() {
		Assert.assertTrue(Whitebox.getInternalState(this.service, Map.class).isEmpty());
		Assert.assertFalse(this.service.isValidPlayer(PLAYER_NAME));
	}

	@Test
	public void testIsValidPlayer_False() {
		this.service.addPlayer(PLAYER_NAME, 2.0, 3.0);
		Assert.assertTrue(this.service.isValidPlayer(PLAYER_NAME));
	}

	@Test
	public void testIsValidBetChoice_True_Even() {
		Assert.assertTrue(this.service.isValidBetChoice("EVEN"));
	}

	@Test
	public void testIsValidBetChoice_True_ODD() {
		Assert.assertTrue(this.service.isValidBetChoice("ODD"));
	}

	@Test
	public void testIsValidBetChoice_True_Number() {
		Assert.assertTrue(this.service.isValidBetChoice("10"));
	}

	@Test
	public void testIsValidBetChoice_False_Number() {
		Assert.assertFalse(this.service.isValidBetChoice("37"));
	}

	@Test
	public void testIsValidBetChoice_False_other() {
		Assert.assertFalse(this.service.isValidBetChoice("other"));
	}

}
