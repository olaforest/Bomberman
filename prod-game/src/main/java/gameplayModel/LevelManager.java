package gameplayModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import static gameplayModel.Levels.getLevels;

@NoArgsConstructor
@AllArgsConstructor
public class LevelManager {
	private static final List<Level> LEVELS = getLevels();

	private int currentLevelIndex;

	public Level getCurrentLevel() {
		return LEVELS.get(currentLevelIndex);
	}
}
