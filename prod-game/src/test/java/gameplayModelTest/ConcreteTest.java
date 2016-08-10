package gameplayModelTest;

import gameplayModel.GridObject;
import gameplayModel.GridObjects.Concrete;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConcreteTest {

	private Concrete concrete;

	@Before
	public void setUp() {
		concrete = new Concrete(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}

	@Test
	public void testGetImage() {
		assertTrue(concrete.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, concrete.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, concrete.getImage().getHeight());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(concrete.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		concrete.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, concrete.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		concrete.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, concrete.getYPosition());
	}
}
