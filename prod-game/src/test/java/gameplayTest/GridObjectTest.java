package gameplayTest;

import gameplayModel.GridMap;
import gameplayModel.GridObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GridObjectTest {
	
	private GridObject gridObj;
	
	@Before
	public void setUp() {
		gridObj = new GridObject(GridObject.EFFECTIVE_PIXEL_WIDTH, GridObject.EFFECTIVE_PIXEL_HEIGHT);
	}
	
	@Test
	public void testIsConcreteCollision() {
		assertFalse(gridObj.isConcreteCollision());
	}
	
	@Test
	public void testGetAndSetXPosition() {
		
		//Tests if the inputed x position is out of bounds on the left.
		gridObj.setXPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, gridObj.getXPosition());
		
		//Tests if the inputed x position is out of bounds on the right.
		gridObj.setXPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, gridObj.getXPosition());
		
		//Tests if the inputed x position is valid.
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, gridObj.getXPosition());

		//Tests if the inputed x position is valid while the y position is above a row, outside of the misalignment tolerance.
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED - 1);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, gridObj.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED - 1, gridObj.getYPosition());

		//Tests if the inputed x position is valid while the y position is above a row, inside of the misalignment tolerance.
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, gridObj.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED + 4, gridObj.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, outside of the misalignment tolerance.
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED + 1);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, gridObj.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED + 1, gridObj.getYPosition());
		
		//Tests if the inputed x position is valid while the y position is below a row, inside of the misalignment tolerance.
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, gridObj.getXPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED - 4, gridObj.getYPosition());
	}
	
	@Test
	public void testGetAndSetYPosition() {
		
		//Tests if the inputed y position is out of bounds on the top.
		gridObj.setYPosition(0);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, gridObj.getYPosition());
		
		//Tests if the inputed y position is out of bounds on the bottom.
		gridObj.setYPosition(GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH, gridObj.getYPosition());
		
		//Tests if the inputed y position is valid.
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 2);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 2, gridObj.getYPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED - 1);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, gridObj.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED - 1, gridObj.getXPosition());

		//Tests if the inputed y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, gridObj.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - GridObject.MISALIGNMENT_ALLOWED + 4, gridObj.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED + 1);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3, gridObj.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED + 1, gridObj.getXPosition());
		
		//Tests if the inputed y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3);
		gridObj.setXPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setYPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4);
		assertEquals(GridObject.EFFECTIVE_PIXEL_WIDTH * 3 + 4, gridObj.getYPosition());
		assertEquals(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + GridObject.MISALIGNMENT_ALLOWED - 4, gridObj.getXPosition());
	}
}
