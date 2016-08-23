package gameplayModelTest;

import gameplayModel.GridObjects.Exitway;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class ExitwayTest {

	private Exitway exitway;

	@Before
	public void setUp() {
		exitway = new Exitway(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testGetImage() {
		assertTrue(exitway.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, exitway.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, exitway.getImage().getHeight());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(exitway.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		exitway.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, exitway.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		exitway.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, exitway.getYPosition());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(exitway.toCSVEntry().size() == 2);
		assertTrue(exitway.toCSVEntry().get(0).equals(Integer.toString(exitway.getXPosition())));
		assertTrue(exitway.toCSVEntry().get(1).equals(Integer.toString(exitway.getYPosition())));
	}
}
