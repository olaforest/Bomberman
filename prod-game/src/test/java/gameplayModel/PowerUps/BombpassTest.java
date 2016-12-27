package gameplayModel.PowerUps;

import gameplayModel.GridObjects.PowerUps.Bombpass;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class BombpassTest {

	private Bombpass bombpass;

	@Before
	public void setUp() {
		bombpass = new Bombpass(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {216, 259};

		assertTrue(bombpass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombpass.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombpass.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(bombpass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombpass.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, bombpass.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertFalse(bombpass.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(bombpass.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		bombpass.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, bombpass.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		bombpass.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, bombpass.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(bombpass.toCSVEntry().size() == 3);
		assertTrue(bombpass.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Bombpass"));
		assertTrue(bombpass.toCSVEntry().get(1).equals(Integer.toString(bombpass.getPosition().getX())));
		assertTrue(bombpass.toCSVEntry().get(2).equals(Integer.toString(bombpass.getPosition().getY())));
	}
}
