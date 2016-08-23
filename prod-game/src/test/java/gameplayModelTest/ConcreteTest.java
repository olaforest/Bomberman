package gameplayModelTest;

import gameplayModel.GridObjects.Concrete;
import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.*;

public class ConcreteTest {

	private Concrete concrete;

	@Before
	public void setUp() {
		concrete = new Concrete(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION);
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

		//Tests if the inputed x position is valid.
		concrete.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, concrete.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		concrete.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, concrete.getYPosition());
	}
}
