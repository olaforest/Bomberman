package gameplayModel.gridObjects;

import org.junit.Before;
import org.junit.Test;

import static gameplayVisual.ImageManager.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;
import static utilities.Position.create;

public class ConcreteTest {

	private Concrete concrete;

	@Before
	public void setUp() {
		concrete = new Concrete(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testGetImage() {
		assertTrue(concrete.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, concrete.getImage().getWidth());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, concrete.getImage().getHeight());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(concrete.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputted x position is valid.
		concrete.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, concrete.getPosition().getX());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputted y position is valid.
		concrete.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, concrete.getPosition().getY());
	}
}
