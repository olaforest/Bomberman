package gameplayModel;

import gameplayModel.gridObjects.PowerUpType;
import gameplayModel.gridObjects.animatedObjects.EnemyType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static gameplayModel.gridObjects.PowerUpType.values;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@EqualsAndHashCode
class Level {
	private static final Predicate<List<Integer>> IS_BONUS_LEVEL = spec -> spec.contains(MAX_VALUE);

	@Getter private final Map<EnemyType, Integer> enemiesCount;
	@Getter private final boolean bonusLevel;
	private final PowerUpType powerUpType;

	Level(@NonNull List<Integer> specification) {
		validateInputSpecification(specification);
		enemiesCount = EnemyType.stream()
				.collect(toMap(identity(), entry -> specification.get(entry.ordinal())));
		bonusLevel = IS_BONUS_LEVEL.test(specification);
		powerUpType = specification.get(8) == 0 ? null : values()[specification.get(8) - 1];
	}

	Optional<PowerUpType> getPowerUpType() {
		return ofNullable(powerUpType);
	}

	EnemyType getHardestEnemyType() {
		return EnemyType.stream()
				.filter(entry -> enemiesCount.get(entry) > 0)
				.reduce((first, second) -> second)
				.orElseThrow(() -> new RuntimeException("There should be at least one enemy type per Level"));
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
			throw new IllegalArgumentException("Invalid power Up type. Value is outside defined range.");
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
