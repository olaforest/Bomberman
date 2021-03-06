package gameplayModel.gridObjects;

import org.junit.Before;
import org.junit.Test;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class ExitwayTest {

	private Exitway exitway;

	@Before
	public void setUp() {
		exitway = new Exitway(create(EFFECTIVE_PIXEL_DIM, EFFECTIVE_PIXEL_DIM));
	}

	@Test
	public void testGetImage() {
		assertTrue(exitway.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIM, exitway.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIM, exitway.getImage().getHeight());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(exitway.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputted x position is valid.
		exitway.setXPosition(EFFECTIVE_PIXEL_DIM * 2);
		assertEquals(EFFECTIVE_PIXEL_DIM * 2, exitway.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputted y position is valid.
		exitway.setYPosition(EFFECTIVE_PIXEL_DIM * 2);
		assertEquals(EFFECTIVE_PIXEL_DIM * 2, exitway.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(exitway.toCSVEntry().size() == 2);
		assertTrue(exitway.toCSVEntry().get(0).equals(Integer.toString(exitway.getPosition().getX())));
		assertTrue(exitway.toCSVEntry().get(1).equals(Integer.toString(exitway.getPosition().getY())));
	}
}
