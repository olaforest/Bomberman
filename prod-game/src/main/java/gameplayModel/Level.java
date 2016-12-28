package gameplayModel;

import java.util.List;

public class Level {
	public Level(List<Integer> specification) {
		if (specification.size() != 9) {
			throw new IllegalArgumentException("Trying to instantiate a level with invalid level specifications");
		}
	}
}
