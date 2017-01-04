package gameplayModel.gridObjects.powerUps;

import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class BombPUTest {

	private BombPU bombPU;

	@Before
	public void setUp() {
		bombPU = new BombPU(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {163, 259};

		assertTrue(bombPU.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombPU.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombPU.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(bombPU.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombPU.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombPU.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertTrue(bombPU.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(bombPU.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		bombPU.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, bombPU.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		bombPU.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, bombPU.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(bombPU.toCSVEntry().size() == 3);
		assertTrue(bombPU.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.BombPU"));
		assertTrue(bombPU.toCSVEntry().get(1).equals(Integer.toString(bombPU.getPosition().getX())));
		assertTrue(bombPU.toCSVEntry().get(2).equals(Integer.toString(bombPU.getPosition().getY())));
	}
}
