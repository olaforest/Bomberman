package gameplayModel;

import org.junit.Before;
import org.junit.Test;

import static gameplayModel.GridObject.EFFECTIVE_PIXEL_DIMENSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static utilities.Position.create;

public class GridObjectTest {

	private GridObject gridObj;

	@Before
	public void setUp() {
		gridObj = new GridObject(create(EFFECTIVE_PIXEL_DIMENSION, EFFECTIVE_PIXEL_DIMENSION));
	}

	@Test
	public void testIsConcreteCollision() {
		assertFalse(gridObj.isConcreteCollision());
	}

	@Test
	public void testGetAndSetXPosition() {

		//Tests if the inputted x position is out of bounds on the left.
		gridObj.setXPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, gridObj.getPosition().getX());

		//Tests if the inputted x position is out of bounds on the right.
		gridObj.setXPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, gridObj.getPosition().getX());

		//Tests if the inputted x position is valid.
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, gridObj.getPosition().getX());

		//Tests if the inputted x position is valid while the y position is above a row, outside of the misalignment tolerance.
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED - 1);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, gridObj.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED - 1, gridObj.getPosition().getY());

		//Tests if the inputted x position is valid while the y position is above a row, inside of the misalignment tolerance.
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, gridObj.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED + 4, gridObj.getPosition().getY());

		//Tests if the inputted x position is valid while the y position is below a row, outside of the misalignment tolerance.
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED + 1);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, gridObj.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED + 1, gridObj.getPosition().getY());

		//Tests if the inputted x position is valid while the y position is below a row, inside of the misalignment tolerance.
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, gridObj.getPosition().getX());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED - 4, gridObj.getPosition().getY());
	}

	@Test
	public void testGetAndSetYPosition() {

		//Tests if the inputted y position is out of bounds on the top.
		gridObj.setYPosition(0);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, gridObj.getPosition().getY());

		//Tests if the inputted y position is out of bounds on the bottom.
		gridObj.setYPosition(GridMap.MAPWIDTH * EFFECTIVE_PIXEL_DIMENSION);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION, gridObj.getPosition().getY());

		//Tests if the inputted y position is valid.
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 2);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 2, gridObj.getPosition().getY());

		//Tests if the inputted y position is valid while the x position is to the left of a column, outside of the misalignment tolerance.
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED - 1);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, gridObj.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED - 1, gridObj.getPosition().getX());

		//Tests if the inputted y position is valid while the x position is to the left of a column, inside of the misalignment tolerance.
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, gridObj.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 - GridObject.MISALIGNMENT_ALLOWED + 4, gridObj.getPosition().getX());

		//Tests if the inputted y position is valid while the x position is to the right of a column, outside of the misalignment tolerance.
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED + 1);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3, gridObj.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED + 1, gridObj.getPosition().getX());

		//Tests if the inputted y position is valid while the x position is to the right of a column, inside of the misalignment tolerance.
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3);
		gridObj.setXPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED);
		gridObj.setYPosition(EFFECTIVE_PIXEL_DIMENSION * 3 + 4);
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + 4, gridObj.getPosition().getY());
		assertEquals(EFFECTIVE_PIXEL_DIMENSION * 3 + GridObject.MISALIGNMENT_ALLOWED - 4, gridObj.getPosition().getX());
	}
}
