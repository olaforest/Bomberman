package gameplayModel.GridObjects.PowerUps;

import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class DetonatorTest {

	private Detonator detonator;

	@Before
	public void setUp() {
		detonator = new Detonator(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {198, 259};

		assertTrue(detonator.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, detonator.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, detonator.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(detonator.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, detonator.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, detonator.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertFalse(detonator.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(detonator.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		detonator.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, detonator.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		detonator.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, detonator.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(detonator.toCSVEntry().size() == 3);
		assertTrue(detonator.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Detonator"));
		assertTrue(detonator.toCSVEntry().get(1).equals(Integer.toString(detonator.getPosition().getX())));
		assertTrue(detonator.toCSVEntry().get(2).equals(Integer.toString(detonator.getPosition().getY())));
	}
}
