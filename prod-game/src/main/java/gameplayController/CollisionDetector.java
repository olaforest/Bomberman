package gameplayController;

import static gameplayModel.GridObject.MISALIGNMENT_ALLOWED;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

import gameplayModel.GridObject;
import gameplayModel.gridObjects.AnimatedObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import gameplayModel.gridObjects.animatedObjects.Brick;
import gameplayModel.gridObjects.animatedObjects.Enemy;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

	public static boolean checkUpCollision(GridObject a, GridObject b) {
		if (a != null && b != null)
			if (abs(a.getX() - b.getX()) < MISALIGNMENT_ALLOWED)
				return b.getY() + EFFECTIVE_PIXEL_DIM > a.getY() && b.getY() <= a.getY();
		return false;
	}

	public static boolean checkDownCollision(GridObject a, GridObject b) {
		if (a != null && b != null)
			if (abs(a.getX() - b.getX()) < MISALIGNMENT_ALLOWED)
				return a.getY() + EFFECTIVE_PIXEL_DIM > b.getY() && a.getY() <= b.getY();
		return false;
	}

	public static boolean checkLeftCollision(GridObject a, GridObject b) {
		if (a != null && b != null)
			if (abs(a.getY() - b.getY()) < MISALIGNMENT_ALLOWED)
				return b.getX() + EFFECTIVE_PIXEL_DIM > a.getX() && b.getX() <= a.getX();
		return false;
	}

	public static boolean checkRightCollision(GridObject a, GridObject b) {
		if (a != null && b != null)
			if (abs(a.getY() - b.getY()) < MISALIGNMENT_ALLOWED)
				return a.getX() + EFFECTIVE_PIXEL_DIM > b.getX() && a.getX() <= b.getX();
		return false;
	}

	public static boolean checkExplGridObject(Bomb bomb, GridObject gridObj) {
		if (abs(bomb.getY() - gridObj.getY()) < EFFECTIVE_PIXEL_DIM) {
			boolean isInRightRange = bomb.getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM >= gridObj.getX() + 6 && bomb.getX() < gridObj.getX();

			if (isInRightRange) return true;

			boolean isInLeftRange = bomb.getX() - (bomb.getLeftRange() + 1) * EFFECTIVE_PIXEL_DIM <= gridObj.getX() - 6 && bomb.getX() > gridObj.getX();

			if (isInLeftRange) return true;
		}

		if (abs(bomb.getX() - gridObj.getX()) < EFFECTIVE_PIXEL_DIM) {
			boolean isInDownRange = bomb.getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM >= gridObj.getY() + 4 && bomb.getY() < gridObj.getY();
			if (isInDownRange) return true;

			return bomb.getY() - (bomb.getUpRange() + 1) * EFFECTIVE_PIXEL_DIM <= gridObj.getY() - 4 && bomb.getY() > gridObj.getY();
		}
		return false;
	}

	public static List<Enemy> checkExplEnemies(List<Enemy> enemies, Bomb bomb) {
		return enemies.stream()
				.filter(enemy -> enemy.isInRangeOf(bomb))
				.collect(toList());
	}

	public static List<AnimatedObject> checkExplBricks(List<Brick> bricks, List<Bomb> bombs, Bomb bomb) {
		final var destroyedObjects = new ArrayList<AnimatedObject>();
		destroyedObjects.add(null);
		destroyedObjects.add(null);
		destroyedObjects.add(null);
		destroyedObjects.add(null);

		for (var brick : bricks) adjustRanges(bomb, brick, destroyedObjects);

		for (Bomb bombOnMap : bombs) {
			if (!bombOnMap.isDead() && !(bombOnMap.getX() == bomb.getX() && bombOnMap.getY() == bomb.getY()))
				adjustRanges(bomb, bombOnMap, destroyedObjects);
		}
		return destroyedObjects;
	}

	private static void adjustRanges(Bomb bomb, AnimatedObject animObj, ArrayList<AnimatedObject> destroyedObjs) {
		if (bomb.getY() == animObj.getY()) {

			boolean isInRightRange = (bomb.getX() + (bomb.getRightRange() + 1) * EFFECTIVE_PIXEL_DIM) >= animObj.getX() && bomb.getX() < animObj.getX();

			if (isInRightRange) {
				int newRightRange = (animObj.getX() - bomb.getX()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getRightRange() > newRightRange) {
					bomb.setRightRange(newRightRange);
					destroyedObjs.set(0, animObj);
				}
			}

			boolean isInLeftRange = (bomb.getX() - (bomb.getLeftRange()) * EFFECTIVE_PIXEL_DIM) <= animObj.getX() && bomb.getX() > animObj.getX();

			if (isInLeftRange) {
				int newLeftRange = (bomb.getX() - animObj.getX()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getLeftRange() > newLeftRange) {
					bomb.setLeftRange(newLeftRange);
					destroyedObjs.set(1, animObj);
				}
			}
		}

		if (bomb.getX() == animObj.getX()) {
			boolean isInDownRange = (bomb.getY() + (bomb.getDownRange() + 1) * EFFECTIVE_PIXEL_DIM) >= animObj.getY() && bomb.getY() < animObj.getY();

			if (isInDownRange) {
				int newDownRange = (animObj.getY() - bomb.getY()) / EFFECTIVE_PIXEL_DIM - 1;

				if (bomb.getDownRange() > newDownRange) {
					bomb.setDownRange(newDownRange);
					destroyedObjs.set(2, animObj);
				}
			}

			boolean isInUpRange = (bomb.getY() - (bomb.getUpRange()) * EFFECTIVE_PIXEL_DIM) <= animObj.getY() && bomb.getY() > animObj.getY();

			if (isInUpRange) {
				int newUpRange = (bomb.getY() - animObj.getY()) / EFFECTIVE_PIXEL_DIM - 1;

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
				&& (abs(bomberman.getX() - b.getX()) < MISALIGNMENT_ALLOWED
				&& bomberman.getY() == b.getY() || (abs(bomberman.getY() - b.getY()) < MISALIGNMENT_ALLOWED
				&& (bomberman.getX() == b.getX())));
	}
}
