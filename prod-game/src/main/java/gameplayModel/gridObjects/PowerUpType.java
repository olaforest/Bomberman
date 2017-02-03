package gameplayModel.gridObjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Arrays.asList;

@Getter
@RequiredArgsConstructor
public enum PowerUpType {
	Bomb(true, asList(2, 328)),
	Flames(true, asList(20, 328)),
	Speed(true, asList(38, 328)),
	WallPass(false, asList(56, 328)),
	Detonator(false, asList(74, 328)),
	BombPass(false, asList(92, 328)),
	FlamePass(false, asList(110, 328)),
	Mystery(false, asList(128, 328));

	private final boolean permanent;
	private final List<Integer> animParam;
}
