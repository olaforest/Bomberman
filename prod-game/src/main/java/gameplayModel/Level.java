package gameplayModel;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.Integer.MAX_VALUE;

public class Level {

	private static final Predicate<List<Integer>> IS_BONUS_LEVEL = spec -> spec.contains(MAX_VALUE);

	@Getter private final List<Integer> enemiesCount;
	@Getter private final boolean bonusLevel;
	private final Integer powerUpType;

	public Level(List<Integer> specification) {
		validateInputSpecification(specification);
		enemiesCount = specification.subList(0, 8);
		bonusLevel = IS_BONUS_LEVEL.test(enemiesCount);
		powerUpType = specification.get(8);
	}

	public Optional<Integer> getPowerUpType() {
		return powerUpType == 0 ? Optional.empty() : Optional.of(powerUpType);
	}

	private void validateInputSpecification(List<Integer> specification) {
		validateSpecListSize(specification);
		validatePowerUpSpec(specification.get(8));
		validateEnemiesCountSpec(specification.subList(0, 8));
	}

	private void validateSpecListSize(List<Integer> specification) {
		if (specification.size() != 9) {
			throw new IllegalArgumentException("Trying to instantiate a level with invalid level specifications");
		}
	}

	private void validatePowerUpSpec(Integer powerUpType) {
		if (powerUpType < 0 || powerUpType > 8) {
			throw new IllegalArgumentException("Invalid power up type. Value is outside defined range.");
		}
	}

	private void validateEnemiesCountSpec(List<Integer> enemiesCount) {
		if (isEnemiesCountNegative(enemiesCount)) {
			throw new IllegalArgumentException("Enemy count cannot be a negative value");
		} else if (IS_BONUS_LEVEL.test(enemiesCount) && isBonusLevelEnemiesCountInvalid(enemiesCount)) {
			throw new IllegalArgumentException("Bonus levels can only have 1 non-zero enemy count value.");
		}
	}

	private boolean isEnemiesCountNegative(List<Integer> counts) {
		return counts.stream()
				.anyMatch(enemyCount -> enemyCount < 0);
	}

	private boolean isBonusLevelEnemiesCountInvalid(List<Integer> counts) {
		return counts.stream()
				.filter(count -> count > 0)
				.count() != 1;
	}
}
