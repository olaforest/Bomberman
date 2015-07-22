package gameplayModelTest.PowerUps;

import gameplayModel.GridObject;
import gameplayModel.GridObjects.PowerUps.BombPU;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BombPUTest {
	
	private BombPU bombPU;
	
	@Before
	public void setUp() {
		bombPU = new BombPU(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testGenerateImage() {
		
		int[] imageParam = {163, 259};
		
		assertTrue(bombPU.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bombPU.generateImage(imageParam).getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, bombPU.generateImage(imageParam).getHeight());
	}
	
	@Test
	public void testGetImage() {
		assertTrue(bombPU.getImage().getClass().toString().equals("class java.awt.image.BufferedImage"));
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, bombPU.getImage().getWidth());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT, bombPU.getImage().getHeight());
	}
	
	@Test
	public void testIsPermanent() {
		assertTrue(bombPU.isPermanent());
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(bombPU.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is valid.
		bombPU.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bombPU.getXPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is valid.
		bombPU.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, bombPU.getYPosition());
	}
	
	@Test
	public void testToCSVEntry() {
		
		assertTrue(bombPU.toCSVEntry().size() == 3);
		assertTrue(bombPU.toCSVEntry().get(0).equals("class gameplayModel.GridObjects.PowerUps.BombPU"));
		assertTrue(bombPU.toCSVEntry().get(1).equals(Integer.toString(bombPU.getXPosition())));
		assertTrue(bombPU.toCSVEntry().get(2).equals(Integer.toString(bombPU.getYPosition())));
	}
}
