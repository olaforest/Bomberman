package gameplayModel.PowerUps;

import gameplayModel.GridObjects.PowerUps.Wallpass;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class WallpassTest {

	private Wallpass wallpass;

	@Before
	public void setUp() {
		wallpass = new Wallpass(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {217, 241};

		assertTrue(wallpass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, wallpass.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, wallpass.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(wallpass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, wallpass.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, wallpass.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertFalse(wallpass.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(wallpass.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		wallpass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, wallpass.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		wallpass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, wallpass.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(wallpass.toCSVEntry().size() == 3);
		assertTrue(wallpass.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Wallpass"));
		assertTrue(wallpass.toCSVEntry().get(1).equals(Integer.toString(wallpass.getPosition().getX())));
		assertTrue(wallpass.toCSVEntry().get(2).equals(Integer.toString(wallpass.getPosition().getY())));
	}
}
