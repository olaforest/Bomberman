package gameplayModel.gridObjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Arrays.asList;

@Getter
@RequiredArgsConstructor
public enum PowerUpType {
	Bomb(true, asList(163, 259)),
	Flames(true, asList(145, 259)),
	Speed(true, asList(180, 259)),
	WallPass(false, asList(217, 241)),
	Detonator(false, asList(198, 259)),
	BombPass(false, asList(216, 259)),
	FlamePass(false, asList(217, 204)),
	Mystery(false, asList(217, 223));

	private final boolean permanent;
	private final List<Integer> animParam;
}
