package gameplayController;

import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static java.util.stream.Collectors.toList;

import gameplayModel.gridObjects.AnimatedObject;
import gameplayModel.gridObjects.animatedObjects.Bomb;
import gameplayModel.gridObjects.animatedObjects.Brick;
import gameplayModel.gridObjects.animatedObjects.Enemy;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

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
}
