package gameplayModelTest.PowerUps;

import gameplayModel.GridObject;
import gameplayModel.GridObjects.PowerUps.Detonator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetonatorTest {

	private Detonator detonator;

	@Before
	public void setUp() {
		detonator = new Detonator(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {198, 259};

		assertTrue(detonator.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, detonator.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, detonator.generateImage(imageParam).getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(detonator.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, detonator.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, detonator.getImage().getHeight());
	}

	@Test
	public void testIsPermanent() {
		assertFalse(detonator.isPermanent());
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(detonator.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputed x position is valid.
		detonator.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, detonator.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		detonator.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, detonator.getYPosition());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(detonator.toCSVEntry().size() == 3);
		assertTrue(detonator.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Detonator"));
		assertTrue(detonator.toCSVEntry().get(1).equals(Integer.toString(detonator.getXPosition())));
		assertTrue(detonator.toCSVEntry().get(2).equals(Integer.toString(detonator.getYPosition())));
	}
}
