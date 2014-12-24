package gameplayTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gameplayModel.Exitway;
import gameplayModel.GridObject;

public class ExitwayTest {
	
	private Exitway exitway;
	
	@Before
	public void setUp() {
		exitway = new Exitway(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGetImage() {
		assertTrue(exitway.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, exitway.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, exitway.getImage().getHeight());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(exitway.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		exitway.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, exitway.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		exitway.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, exitway.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(exitway.toCSVEntry().size() == 2);
		assertTrue(exitway.toCSVEntry().get(0).equals(Integer.toString(exitway.getXPosition())));
		assertTrue(exitway.toCSVEntry().get(1).equals(Integer.toString(exitway.getYPosition())));
	}
}
