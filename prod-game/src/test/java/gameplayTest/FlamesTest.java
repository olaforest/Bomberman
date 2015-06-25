package gameplayTest;

import gameplayModel.Flames;
import gameplayModel.GridObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlamesTest {
	
	private Flames flames;
	
	@Before
	public void setUp() {
		flames = new Flames(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGenerateImage() {
		
		int[] imageParam = {145, 259};
		
		assertTrue(flames.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, flames.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, flames.generateImage(imageParam).getHeight());
	}
	
	@Test
	public void testGetImage() {
		assertTrue(flames.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, flames.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, flames.getImage().getHeight());
	}
	
	@Test
	public void testIsPermanent() {
		assertTrue(flames.isPermanent());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(flames.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		flames.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, flames.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		flames.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, flames.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(flames.toCSVEntry().size() == 3);
		assertTrue(flames.toCSVEntry().get(0).equals("class gameplayModel.Flames"));
		assertTrue(flames.toCSVEntry().get(1).equals(Integer.toString(flames.getXPosition())));
		assertTrue(flames.toCSVEntry().get(2).equals(Integer.toString(flames.getYPosition())));
	}
}
