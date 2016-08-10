package gameplayModelTest.PowerUps;

import gameplayModel.GridObject;
import gameplayModel.GridObjects.PowerUps.Bombpass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BombpassTest {

	private Bombpass bombpass;

	@Before
	public void setUp() {
		bombpass = new Bombpass(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}

	@Test
	public void testGenerateImage() {

		int[] imageParam = {216, 259};

		assertTrue(bombpass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bombpass.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, bombpass.generateImage(imageParam).getHeight());
	}

	@Test
	public void testGetImage() {
		assertTrue(bombpass.getImage().getClass().toString().equals("class java.awt.sprite.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bombpass.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, bombpass.getImage().getHeight());
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
		bombpass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bombpass.getXPosition());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputed y position is valid.
		bombpass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bombpass.getYPosition());
	}

	@Test
	public void testToCSVEntry() {

		assertTrue(bombpass.toCSVEntry().size() == 3);
		assertTrue(bombpass.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Bombpass"));
		assertTrue(bombpass.toCSVEntry().get(1).equals(Integer.toString(bombpass.getXPosition())));
		assertTrue(bombpass.toCSVEntry().get(2).equals(Integer.toString(bombpass.getYPosition())));
	}
}
