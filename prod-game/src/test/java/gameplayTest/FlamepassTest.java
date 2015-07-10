package gameplayTest;

import gameplayModel.GridObject;
import gameplayModel.PowerUps.Flamepass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlamepassTest {
	
	private Flamepass flamepass;
	
	@Before
	public void setUp() {
		flamepass = new Flamepass(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGenerateImage() {
		
		int[] imageParam = {217, 204};
		
		assertTrue(flamepass.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, flamepass.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, flamepass.generateImage(imageParam).getHeight());
	}
	
	@Test
	public void testGetImage() {
		assertTrue(flamepass.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, flamepass.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, flamepass.getImage().getHeight());
	}
	
	@Test
	public void testIsPermanent() {
		assertFalse(flamepass.isPermanent());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(flamepass.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		flamepass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, flamepass.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		flamepass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, flamepass.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(flamepass.toCSVEntry().size() == 3);
		assertTrue(flamepass.toCSVEntry().get(0).equals("class gameplayModel.PowerUps.Flamepass"));
		assertTrue(flamepass.toCSVEntry().get(1).equals(Integer.toString(flamepass.getXPosition())));
		assertTrue(flamepass.toCSVEntry().get(2).equals(Integer.toString(flamepass.getYPosition())));
	}
}
