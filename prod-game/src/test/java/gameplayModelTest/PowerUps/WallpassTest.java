package gameplayModelTest.PowerUps;

import gameplayModel.GridObject;
import gameplayModel.GridObjects.PowerUps.Wallpass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WallpassTest {
	
	private Wallpass wallpass;
	
	@Before
	public void setUp() {
		wallpass = new Wallpass(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGenerateImage() {
		
		int[] imageParam = {217, 241};
		
		assertTrue(wallpass.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, wallpass.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, wallpass.generateImage(imageParam).getHeight());
	}
	
	@Test
	public void testGetImage() {
		assertTrue(wallpass.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, wallpass.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, wallpass.getImage().getHeight());
	}
	
	@Test
	public void testIsPermanent() {
		assertFalse(wallpass.isPermanent());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(wallpass.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		wallpass.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, wallpass.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		wallpass.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, wallpass.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(wallpass.toCSVEntry().size() == 3);
		assertTrue(wallpass.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.Wallpass"));
		assertTrue(wallpass.toCSVEntry().get(1).equals(Integer.toString(wallpass.getXPosition())));
		assertTrue(wallpass.toCSVEntry().get(2).equals(Integer.toString(wallpass.getYPosition())));
	}
}
