package gameplayModelTest;

import gameplayModel.GridObjects.PowerUp;
import gameplayModel.GridObjects.PowerUps.Bombpass;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utility.Position.create;

public class PowerUpTest {

	private PowerUp powerUp;

	@Before
	public void setUp() {
		powerUp = new Bombpass(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {217, 241};

		assertTrue(powerUp.getImage() == null);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, powerUp.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, powerUp.generateImage().getHeight());
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
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, powerUp.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		powerUp.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, powerUp.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(powerUp.toCSVEntry().size() == 3);
		assertTrue(powerUp.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUp"));
		assertTrue(powerUp.toCSVEntry().get(1).equals(Integer.toString(powerUp.getPosition().getX())));
		assertTrue(powerUp.toCSVEntry().get(2).equals(Integer.toString(powerUp.getPosition().getY())));
	}
}
