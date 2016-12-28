package gameplayModel;

import lombok.Getter;

import java.util.List;

@Getter
public class Level {
	private List<Integer> enemiesType;
	private Integer powerUpType;

	public Level(List<Integer> specification) {
		if (specification.size() != 9) {
			throw new IllegalArgumentException("Trying to instantiate a level with invalid level specifications");
		}
		enemiesType = specification.subList(0, 8);
		powerUpType = specification.get(8);
	}
}
