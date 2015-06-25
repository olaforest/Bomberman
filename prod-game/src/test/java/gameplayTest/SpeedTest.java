package gameplayTest;

import gameplayModel.GridObject;
import gameplayModel.Speed;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpeedTest {
	
	private Speed speed;
	
	@Before
	public void setUp() {
		speed = new Speed(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGenerateImage() {
		
		int[] imageParam = {180, 259};
		
		assertTrue(speed.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, speed.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, speed.generateImage(imageParam).getHeight());
	}
	
	@Test
	public void testGetImage() {
		assertTrue(speed.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, speed.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, speed.getImage().getHeight());
	}
	
	@Test
	public void testIsPermanent() {
		assertTrue(speed.isPermanent());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(speed.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		speed.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, speed.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		speed.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, speed.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(speed.toCSVEntry().size() == 3);
		assertTrue(speed.toCSVEntry().get(0).equals("class gameplayModel.Speed"));
		assertTrue(speed.toCSVEntry().get(1).equals(Integer.toString(speed.getXPosition())));
		assertTrue(speed.toCSVEntry().get(2).equals(Integer.toString(speed.getYPosition())));
	}
}
