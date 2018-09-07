package gameplayModel.gridObjects;

import gameplayModel.gridObjects.animatedObjects.Bomberman;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

@Getter
@RequiredArgsConstructor
public enum PowerUpType {
	Bomb(true, asList(2, 328), Bomberman::increaseBombAvailable),
	Flames(true, asList(20, 328), Bomberman::increaseBombRange),
	Speed(true, asList(38, 328), Bomberman::increaseSpeed),
	WallPass(false, asList(56, 328), Bomberman::activateCanWallPass),
	Detonator(false, asList(74, 328), Bomberman::activateCanDetonateBomb),
	BombPass(false, asList(92, 328), Bomberman::activateCanBombPass),
	FlamePass(false, asList(110, 328), Bomberman::activateCanFlamePass),
	Mystery(false, asList(128, 328), Bomberman::activateInvincibility);

	private final boolean permanent;
	private final List<Integer> animParam;
	private final Consumer<Bomberman> action;
}
