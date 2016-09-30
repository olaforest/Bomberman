package gameplayModelTest.PowerUps;

import gameplayModel.GridObjects.PowerUps.Speed;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class SpeedTest {

	private Speed speed;

	@Before
	public void setUp() {
		speed = new Speed(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION, -1);
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {180, 259};

		assertTrue(speed.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, speed.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, speed.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(speed.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, speed.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, speed.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertTrue(speed.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(speed.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		speed.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, speed.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		speed.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, speed.getYPosition());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(speed.toCSVEntry().size() == 3);
		assertTrue(speed.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Speed"));
		assertTrue(speed.toCSVEntry().get(1).equals(Integer.toString(speed.getXPosition())));
		assertTrue(speed.toCSVEntry().get(2).equals(Integer.toString(speed.getYPosition())));
	}
}
