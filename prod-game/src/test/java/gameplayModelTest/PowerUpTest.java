package gameplayModelTest;

import gameplayModel.GridObjects.PowerUp;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class PowerUpTest {

	private PowerUp powerUp;

	@Before
	public void setUp() {
		powerUp = new PowerUp(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {217, 241};

		assertTrue(powerUp.getImage() == null);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, powerUp.generateImage(imageParam).getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, powerUp.generateImage(imageParam).getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(powerUp.getImage() == null);
	}

	@Test
	public void testIsPermanent() {
		assertFalse(powerUp.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(powerUp.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		powerUp.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, powerUp.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		powerUp.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, powerUp.getYPosition());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(powerUp.toCSVEntry().size() == 3);
		assertTrue(powerUp.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUp"));
		assertTrue(powerUp.toCSVEntry().get(1).equals(Integer.toString(powerUp.getXPosition())));
		assertTrue(powerUp.toCSVEntry().get(2).equals(Integer.toString(powerUp.getYPosition())));
	}
}
