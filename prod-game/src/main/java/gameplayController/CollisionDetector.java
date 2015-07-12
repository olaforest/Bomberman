package gameplayController;

import gameplayModel.GameContext;
import gameplayModel.GridObject;
import gameplayModel.GridObjects.AnimatedObject;
import gameplayModel.GridObjects.AnimatedObjects.Bomb;
import gameplayModel.GridObjects.AnimatedObjects.Bomberman;
import gameplayModel.GridObjects.AnimatedObjects.Brick;
import gameplayModel.GridObjects.AnimatedObjects.Enemy;

import java.util.ArrayList;

public class CollisionDetector {

	private GameContext gameContext;
	private ArrayList<Brick> bricks;
	private ArrayList<Enemy> enemies;

	public CollisionDetector(GameContext gC) {
		gameContext = gC;
		bricks = gameContext.getGridMap().getBricks();
		enemies = gameContext.getGridMap().getEnemies();
	}

	public boolean checkUpCollision(GridObject a, GridObject b) {
		if((a != null) && (b != null))
	                if (Math.abs(a.getXPosition() - b.getXPosition()) < GridObject.MISALIGNMENT_ALLOWED)
	                    return ((b.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT > a.getYPosition()) && (b.getYPosition() <= a.getYPosition()));
		return false;
	}

	public boolean checkDownCollision(GridObject a, GridObject b) {
		if((a != null) && (b != null))
	                if (Math.abs(a.getXPosition() - b.getXPosition()) < GridObject.MISALIGNMENT_ALLOWED)
			return ((a.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT > b.getYPosition()) && (a.getYPosition() <= b.getYPosition()));
		return false;
	}

	public boolean checkLeftCollision(GridObject a, GridObject b) {
		if((a != null) && (b != null))
	                if (Math.abs(a.getYPosition() - b.getYPosition()) < GridObject.MISALIGNMENT_ALLOWED)
			return((b.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH > a.getXPosition()) && (b.getXPosition() <= a.getXPosition()));
		return false;
	}

	public boolean checkRightCollision(GridObject a, GridObject b) {
	        if((a != null) && (b != null))
	            if (Math.abs(a.getYPosition() - b.getYPosition()) < GridObject.MISALIGNMENT_ALLOWED)
	                return ((a.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH > b.getXPosition()) && (a.getXPosition() <= b.getXPosition()));
	        return false;
	}

	public boolean checkExplGridObject(Bomb bomb, GridObject gridObj) {
		if (Math.abs(bomb.getYPosition() - gridObj.getYPosition()) < GridObject.EFFECTIVE_PIXEL_HEIGHT) {
			boolean isInRightRange = (bomb.getXPosition() + (bomb.getRightRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) >= (gridObj.getXPosition() + 6) && bomb.getXPosition() < gridObj.getXPosition();

			if (isInRightRange) { return true; }

			boolean isInLeftRange = (bomb.getXPosition() - (bomb.getLeftRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) <= (gridObj.getXPosition() - 6) && bomb.getXPosition() > gridObj.getXPosition();

			if (isInLeftRange) { return true; }
		}

		if (Math.abs(bomb.getXPosition() - gridObj.getXPosition()) < GridObject.EFFECTIVE_PIXEL_WIDTH) {
			boolean isInDownRange = (bomb.getYPosition() + (bomb.getDownRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) >= (gridObj.getYPosition() + 4) && bomb.getYPosition() < gridObj.getYPosition();
			if (isInDownRange) { return true; }

			boolean isInUpRange = (bomb.getYPosition() - (bomb.getUpRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) <= (gridObj.getYPosition() - 4) && bomb.getYPosition() > gridObj.getYPosition();
			if (isInUpRange) { return true; }
		}
		return false;
	}

	public ArrayList<Enemy> checkExplEnemies(Bomb bomb) {
		ArrayList<Enemy> destroyedEnemies = new ArrayList<>();

		for (Enemy enemy : enemies) {
			if (Math.abs(bomb.getYPosition() - enemy.getYPosition()) < GridObject.EFFECTIVE_PIXEL_HEIGHT) {
				boolean isInRightRange = (bomb.getXPosition() + (bomb.getRightRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) >= (enemy.getXPosition() + 1) && bomb.getXPosition() < enemy.getXPosition();
				if (isInRightRange) { destroyedEnemies.add(enemy); }

				boolean isInLeftRange = (bomb.getXPosition() - (bomb.getLeftRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) <= (enemy.getXPosition() - 1) && bomb.getXPosition() > enemy.getXPosition();
				if (isInLeftRange) { destroyedEnemies.add(enemy); }
			}

			if (Math.abs(bomb.getXPosition() - enemy.getXPosition()) < GridObject.EFFECTIVE_PIXEL_WIDTH) {
				boolean isInDownRange = (bomb.getYPosition() + (bomb.getDownRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) >= (enemy.getYPosition() + 1) && bomb.getYPosition() < enemy.getYPosition();
				if (isInDownRange) { destroyedEnemies.add(enemy); }

				boolean isInUpRange = (bomb.getYPosition() - (bomb.getUpRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) <= (enemy.getYPosition() - 1) && bomb.getYPosition() > enemy.getYPosition();
				if (isInUpRange) { destroyedEnemies.add(enemy); }
			}
		}
		return destroyedEnemies;
	}

	public ArrayList<AnimatedObject> checkExplBricks(Bomb bomb) {
		ArrayList<AnimatedObject> destroyedObjs = new ArrayList<>();
		destroyedObjs.add(null);
		destroyedObjs.add(null);
		destroyedObjs.add(null);
		destroyedObjs.add(null);

		for (Brick brick : bricks) { adjustRanges(bomb, brick, destroyedObjs); }

		for (Bomb bombOnMap : gameContext.getGridMap().getBombs()) {
			if (!bombOnMap.isDead() && !(bombOnMap.getXPosition() == bomb.getXPosition() && bombOnMap.getYPosition() == bomb.getYPosition()))
				adjustRanges(bomb, bombOnMap, destroyedObjs);
		}
		return destroyedObjs;
	}

	private void adjustRanges(Bomb bomb, AnimatedObject animObj,ArrayList<AnimatedObject> destroyedObjs) {
		if (bomb.getYPosition() == animObj.getYPosition()) {

			boolean isInRightRange = (bomb.getXPosition() + (bomb.getRightRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) >= animObj.getXPosition() && bomb.getXPosition() < animObj.getXPosition();

			if (isInRightRange) {
				int newRightRange = (animObj.getXPosition() - bomb.getXPosition()) / GridObject.EFFECTIVE_PIXEL_WIDTH - 1;

				if (bomb.getRightRange() > newRightRange) {
					bomb.setRightRange(newRightRange);
					destroyedObjs.set(0, animObj);
				}
			}

			boolean isInLeftRange = (bomb.getXPosition() - (bomb.getLeftRange()) * GridObject.EFFECTIVE_PIXEL_WIDTH) <= animObj.getXPosition() && bomb.getXPosition() > animObj.getXPosition();

			if (isInLeftRange) {
				int newLeftRange = (bomb.getXPosition() - animObj.getXPosition()) / GridObject.EFFECTIVE_PIXEL_WIDTH - 1;

				if (bomb.getLeftRange() > newLeftRange) {
					bomb.setLeftRange(newLeftRange);
					destroyedObjs.set(1, animObj);
				}
			}
		}

		if (bomb.getXPosition() == animObj.getXPosition()) {
			boolean isInDownRange = (bomb.getYPosition() + (bomb.getDownRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) >= animObj.getYPosition() && bomb.getYPosition() < animObj.getYPosition();

			if (isInDownRange) {
				int newDownRange = (animObj.getYPosition() - bomb.getYPosition()) / GridObject.EFFECTIVE_PIXEL_HEIGHT - 1;

				if (bomb.getDownRange() > newDownRange) {
					bomb.setDownRange(newDownRange);
					destroyedObjs.set(2, animObj);
				}
			}

			boolean isInUpRange = (bomb.getYPosition() - (bomb.getUpRange()) * GridObject.EFFECTIVE_PIXEL_HEIGHT) <= animObj.getYPosition() && bomb.getYPosition() > animObj.getYPosition();

			if (isInUpRange) {
				int newUpRange = (bomb.getYPosition() - animObj.getYPosition()) / GridObject.EFFECTIVE_PIXEL_HEIGHT - 1;

				if (bomb.getUpRange() > newUpRange) {
					bomb.setUpRange(newUpRange);
					destroyedObjs.set(3, animObj);
				}
			}
		}
	}

	public boolean checkExactCollision(Bomberman bomberman, GridObject b) {
		return bomberman != null
				&& b != null
				&& ((Math.abs(bomberman.getXPosition() - b.getXPosition()) < Bomberman.MISALIGNMENT_ALLOWED
				&& (bomberman.getYPosition() == b.getYPosition())) || (Math.abs(bomberman.getYPosition() - b.getYPosition()) < Bomberman.MISALIGNMENT_ALLOWED
				&& (bomberman.getXPosition() == b.getXPosition())));
	}
}