package gameplayModel.GridObjects.PowerUps;

import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class MysteryTest {

	private Mystery mystery;

	@Before
	public void setUp() {
		mystery = new Mystery(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {217, 223};

		assertTrue(mystery.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, mystery.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, mystery.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(mystery.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, mystery.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, mystery.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertFalse(mystery.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(mystery.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		mystery.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, mystery.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		mystery.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, mystery.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(mystery.toCSVEntry().size() == 3);
		assertTrue(mystery.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Mystery"));
		assertTrue(mystery.toCSVEntry().get(1).equals(Integer.toString(mystery.getPosition().getX())));
		assertTrue(mystery.toCSVEntry().get(2).equals(Integer.toString(mystery.getPosition().getY())));
	}
}
