package gameplayTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gameplayModel.Mystery;
import gameplayModel.GridObject;

public class MysteryTest {
	
	private Mystery mystery;
	
	@Before
	public void setUp() {
		mystery = new Mystery(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGenerateImage() {
		
		int[] imageParam = {217, 223};
		
		assertTrue(mystery.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, mystery.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, mystery.generateImage(imageParam).getHeight());
	}
	
	@Test
	public void testGetImage() {
		assertTrue(mystery.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, mystery.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, mystery.getImage().getHeight());
	}
	
	@Test
	public void testIsPermanent() {
		assertFalse(mystery.isPermanent());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(mystery.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		mystery.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, mystery.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		mystery.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, mystery.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(mystery.toCSVEntry().size() == 3);
		assertTrue(mystery.toCSVEntry().get(0).equals("class gameplayModel.Mystery"));
		assertTrue(mystery.toCSVEntry().get(1).equals(Integer.toString(mystery.getXPosition())));
		assertTrue(mystery.toCSVEntry().get(2).equals(Integer.toString(mystery.getYPosition())));
	}
}
