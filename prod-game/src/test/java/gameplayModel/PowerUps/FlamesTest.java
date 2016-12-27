package gameplayModel.PowerUps;

import gameplayModel.GridObjects.PowerUps.Flames;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class FlamesTest {

	private Flames flames;

	@Before
	public void setUp() {
		flames = new Flames(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {145, 259};

		assertTrue(flames.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flames.generateImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flames.generateImage().getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(flames.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flames.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, flames.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertTrue(flames.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(flames.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		flames.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, flames.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		flames.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, flames.getPosition().getY());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(flames.toCSVEntry().size() == 3);
		assertTrue(flames.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Flames"));
		assertTrue(flames.toCSVEntry().get(1).equals(Integer.toString(flames.getPosition().getX())));
		assertTrue(flames.toCSVEntry().get(2).equals(Integer.toString(flames.getPosition().getY())));
	}
}
