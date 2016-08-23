package gameplayModelTest.PowerUps;

import gameplayModel.GridObjects.PowerUps.Flamepass;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class FlamepassTest {

	private Flamepass flamepass;

	@Before
	public void setUp() {
		flamepass = new Flamepass(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {217, 204};

		assertTrue(flamepass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flamepass.generateImage(imageParam).getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flamepass.generateImage(imageParam).getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(flamepass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flamepass.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flamepass.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertFalse(flamepass.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(flamepass.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		flamepass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, flamepass.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		flamepass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, flamepass.getYPosition());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(flamepass.toCSVEntry().size() == 3);
		assertTrue(flamepass.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Flamepass"));
		assertTrue(flamepass.toCSVEntry().get(1).equals(Integer.toString(flamepass.getXPosition())));
		assertTrue(flamepass.toCSVEntry().get(2).equals(Integer.toString(flamepass.getYPosition())));
	}
}
