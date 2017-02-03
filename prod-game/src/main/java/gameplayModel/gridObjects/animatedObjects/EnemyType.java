package gameplayModel.gridObjects.animatedObjects;

import gameplayView.AnimParam;
import gameplayView.AnimationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static gameplayModel.gridObjects.animatedObjects.Enemy.ANIMATION_TYPES;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Getter
@RequiredArgsConstructor
public enum EnemyType {
	Balloom(100, 2, 1, false, generateAnimParam(asList(2, 39, 4, 74, 39, 4, 146, 39, 5))),
	Oneal(200, 3, 2, false, generateAnimParam(asList(2, 57, 4, 74, 57, 4, 146, 57, 5))),
	Doll(400, 3, 1, false, generateAnimParam(asList(2, 111, 4, 74, 111, 4, 146, 111, 5))),
	Minvo(800, 4, 2, false, generateAnimParam(asList(2, 129, 4, 74, 129, 4, 146, 129, 5))),
	Kondoria(1000, 1, 3, true, generateAnimParam(asList(2, 147, 4, 74, 147, 4, 146, 147, 5))),
	Ovapi(2000, 2, 2, true, generateAnimParam(asList(2, 165, 4, 74, 165, 4, 146, 165, 5))),
	Pass(4000, 4, 3, false, generateAnimParam(asList(2, 183, 4, 74, 183, 4, 146, 183, 5))),
	Pontan(8000, 4, 3, true, generateAnimParam(asList(2, 93, 4, 74, 93, 4, 146, 93, 5)));

	private final int points;
	private final int speed;
	private final int smartness;
	private final boolean wallpass;
	private final List<SimpleEntry<AnimationType, AnimParam>> animParams;

	public static Stream<EnemyType> stream() {
		return Arrays.stream(values());
	}

	private static List<SimpleEntry<AnimationType, AnimParam>> generateAnimParam(List<Integer> params) {
		final List<AnimParam> animParams = convertToAnimParam(params);
		return IntStream.range(0, animParams.size())
				.mapToObj(i -> new SimpleEntry<>(ANIMATION_TYPES.get(i), animParams.get(i)))
				.collect(toList());
	}

	private static List<AnimParam> convertToAnimParam(List<Integer> params) {
		return IntStream.range(0, 3)
				.mapToObj(i -> params.subList(i * 3, (i + 1) * 3))
				.map(subList -> new AnimParam(subList.get(0), subList.get(1), subList.get(2)))
				.collect(toList());
	}
}
