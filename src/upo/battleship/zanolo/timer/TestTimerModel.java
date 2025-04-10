/**
 * 
 */
package upo.battleship.zanolo.timer;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * @author luca
 *
 */
class TestTimerModel {

	private static TimerModel timer;
	private static TimerView view;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		timer = new TimerModel(10);
		view = new TimerView(timer);
		new TimerController(view,timer);
	}

	/**
	 * Test method for {@link upo.upo.battleship.zanolo.Oggetti.TimerModel#TimerModel(int)}.
	 */
	@Test
	void testTimerModel() {
		assertTrue(timer.t.isAlive());
	}

	@Test
	void testToString() {
		assertEquals(timer.toString(), "00:00");
	}
	
	@Test
	void testIncrease() throws InterruptedException {
		int a,b;
		a = timer.getSeconds();
		Thread.sleep(1000);
		b = timer.getSeconds();
		assertFalse(a == b);
		
	}
}
