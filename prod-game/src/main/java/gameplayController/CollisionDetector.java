package gameplayController;

import gameplayModel.GridObject;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayModel.gridObjects.animatedObjects.*;

import java.util.ArrayList;
import java.util.List;

import static gameplayModel.GridObject.MISALIGNMENT_ALLOWED;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;

public class CollisionDetector {

	public static boolean checkUpCollision(GridObject a, GridObject b) {
		if ((a != null) && (b != null))
			if (Math.abs(a.getPosition().getX() - b.getPosition().getX()) < MISALIGNMENT_ALLOWED)
				return ((b.getPosition().getY() + EFFECTIVE_PIXEL_DIM > a.getPosition().getY()) && (b.getPosition().getY() <= a.getPosition().getY()));
		return false;
	}

	public static boolean checkDownCollision(GridObject a, GridObject b) {
		if ((a != null) && (b != null))
			if (Math.abs(a.getPosition().getX() - b.getPosition().getX()) < MISALIGNMENT_ALLOWED)
				return ((a.getPosition().getY() + EFFECTIVE_PIXEL_DIM > b.getPosition().getY()) && (a.getPosition().getY() <= b.getPosition().getY()));
		return false;
	}

	public static boolean checkLeftCollision(GridObject a, GridObject b) {
		if ((a != null) && (b != null))
			if (Math.abs(a.getPosition().getY() - b.getPosition().getY()) < MISALIGNMENT_ALLOWED)
				return ((b.getPosition().getX() + EFFECTIVE_PIXEL_DIM > a.getPosition().getX()) && (b.getPosition().getX() <= a.getPosition().getX()));
		return false;
	}

	public static boolean checkRightCollision(GridObject a, GridObject b) {
		if ((a != null) && (b != null))
			if (Math.abs(a.getPosition().getY() - b.getPosition().getY()) < MISALIGNMENT_ALLOWED)
				return ((a.getPosition().getX() + EFFECTIVE_PIXEL_DIM > b.getPosition().getX()) && (a.getPosition().getX() <= b.getPosition().getX()));
		return false;
	}

	public static boolean checkExplGridObject(Bomb bomb, GridObject gridObj) {
		if (Math.abs(bomb.getPosition().getY() - gridObj.getPosition().getY()) < EFFECTIVE_PIXEL_DIM) {
			boolean isInRightRange = (bomb.getPosition().getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM) >= (gridObj.getPosition().getX() + 6) && bomb.getPosition().getX() < gridObj.getPosition().getX();

			if (isInRightRange) return true;

			boolean isInLeftRange = (bomb.getPosition().getX() - (bomb.getLeftRange() + 1) * EFFECTIVE_PIXEL_DIM) <= (gridObj.getPosition().getX() - 6) && bomb.getPosition().getX() > gridObj.getPosition().getX();

			if (isInLeftRange) return true;
		}

		if (Math.abs(bomb.getPosition().getX() - gridObj.getPosition().getX()) < EFFECTIVE_PIXEL_DIM) {
			boolean isInDownRange = (bomb.getPosition().getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM) >= (gridObj.getPosition().getY() + 4) && bomb.getPosition().getY() < gridObj.getPosition().getY();
			if (isInDownRange) return true;

			boolean isInUpRange = (bomb.getPosition().getY() - (bomb.getUpRange() + 1) * EFFECTIVE_PIXEL_DIM) <= (gridObj.getPosition().getY() - 4) && bomb.getPosition().getY() > gridObj.getPosition().getY();
			if (isInUpRange) return true;
		}
		return false;
	}

	public static ArrayList<Enemy> checkExplEnemies(List<Enemy> enemies, Bomb bomb) {
		ArrayList<Enemy> destroyedEnemies = new ArrayList<>();

		for (Enemy enemy : enemies) {
			if (Math.abs(bomb.getPosition().getY() - enemy.getPosition().getY()) < EFFECTIVE_PIXEL_DIM) {
				boolean isInRightRange = (bomb.getPosition().getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM) >= (enemy.getPosition().getX() + 1) && bomb.getPosition().getX() < enemy.getPosition().getX();
				if (isInRightRange) destroyedEnemies.add(enemy);

				boolean isInLeftRange = (bomb.getPosition().getX() - (bomb.getLeftRange() + 1) * EFFECTIVE_PIXEL_DIM) <= (enemy.getPosition().getX() - 1) && bomb.getPosition().getX() > enemy.getPosition().getX();
				if (isInLeftRange) destroyedEnemies.add(enemy);
			}

			if (Math.abs(bomb.getPosition().getX() - enemy.getPosition().getX()) < EFFECTIVE_PIXEL_DIM) {
				boolean isInDownRange = (bomb.getPosition().getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM) >= (enemy.getPosition().getY() + 1) && bomb.getPosition().getY() < enemy.getPosition().getY();
				if (isInDownRange) destroyedEnemies.add(enemy);

				boolean isInUpRange = (bomb.getPosition().getY() - (bomb.getUpRange() + 1) * EFFECTIVE_PIXEL_DIM) <= (enemy.getPosition().getY() - 1) && bomb.getPosition().getY() > enemy.getPosition().getY();
				if (isInUpRange) destroyedEnemies.add(enemy);
			}
		}
		return destroyedEnemies;
	}

	public static List<AnimatedObject> checkExplBricks(List<Brick> bricks, List<Bomb> bombs, Bomb bomb) {
		ArrayList<AnimatedObject> destroyedObjs = new ArrayList<>();
		destroyedObjs.add(null);
		destroyedObjs.add(null);
		destroyedObjs.add(null);
		destroyedObjs.add(null);

		for (Brick brick : bricks) adjustRanges(bomb, brick, destroyedObjs);

		for (Bomb bombOnMap : bombs) {
			if (!bombOnMap.isDead() && !(bombOnMap.getPosition().getX() == bomb.getPosition().getX() && bombOnMap.getPosition().getY() == bomb.getPosition().getY()))
				adjustRanges(bomb, bombOnMap, destroyedObjs);
		}
		return destroyedObjs;
	}

	private static void adjustRanges(Bomb bomb, AnimatedObject animObj, ArrayList<AnimatedObject> destroyedObjs) {
		if (bomb.getPosition().getY() == animObj.getPosition().getY()) {

			boolean isInRightRange = (bomb.getPosition().getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM) >= animObj.getPosition().getX() && bomb.getPosition().getX() < animObj.getPosition().getX();

			if (isInRightRange) {
				int newRightRange = (animObj.getPosition().getX() - bomb.getPosition().getX()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getRightRange() > newRightRange) {
					bomb.setRightRange(newRightRange);
					destroyedObjs.set(0, animObj);
				}
			}

			boolean isInLeftRange = (bomb.getPosition().getX() - (bomb.getLeftRange()) * EFFECTIVE_PIXEL_DIM) <= animObj.getPosition().getX() && bomb.getPosition().getX() > animObj.getPosition().getX();

			if (isInLeftRange) {
				int newLeftRange = (bomb.getPosition().getX() - animObj.getPosition().getX()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getLeftRange() > newLeftRange) {
					bomb.setLeftRange(newLeftRange);
					destroyedObjs.set(1, animObj);
				}
			}
		}

		if (bomb.getPosition().getX() == animObj.getPosition().getX()) {
			boolean isInDownRange = (bomb.getPosition().getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM) >= animObj.getPosition().getY() && bomb.getPosition().getY() < animObj.getPosition().getY();

			if (isInDownRange) {
				int newDownRange = (animObj.getPosition().getY() - bomb.getPosition().getY()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getDownRange() > newDownRange) {
					bomb.setDownRange(newDownRange);
					destroyedObjs.set(2, animObj);
				}
			}

			boolean isInUpRange = (bomb.getPosition().getY() - (bomb.getUpRange()) * EFFECTIVE_PIXEL_DIM) <= animObj.getPosition().getY() && bomb.getPosition().getY() > animObj.getPosition().getY();

			if (isInUpRange) {
				int newUpRange = (bomb.getPosition().getY() - animObj.getPosition().getY()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getUpRange() > newUpRange) {
					bomb.setUpRange(newUpRange);
					destroyedObjs.set(3, animObj);
				}
			}
		}
	}

	public static boolean checkExactCollision(Bomberman bomberman, GridObject b) {
		return bomberman != null
				&& b != null
				&& ((Math.abs(bomberman.getPosition().getX() - b.getPosition().getX()) < MISALIGNMENT_ALLOWED
				&& (bomberman.getPosition().getY() == b.getPosition().getY())) || (Math.abs(bomberman.getPosition().getY() - b.getPosition().getY()) < MISALIGNMENT_ALLOWED
				&& (bomberman.getPosition().getX() == b.getPosition().getX())));
	}
}
