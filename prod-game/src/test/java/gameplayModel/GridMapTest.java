package gameplayModel;

import static gameplayModel.GridMap.*;
import static gameplayModel.LevelManager.importLevels;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIM;
import static org.assertj.core.api.Assertions.assertThat;

import gameplayModel.gridObjects.Concrete;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Function;

public class GridMapTest {

	@Test
	public void initialLevel_newInstance_returnsGridMapWithBombermanAtInitialPosition() {
		//given
		final Level level = importLevels().get(0);
		//when
		final Bomberman bomberman = new GridMap(level).getBomberman();
		//then
		assertThat(bomberman.getX()).isEqualTo(EFFECTIVE_PIXEL_DIM);
		assertThat(bomberman.getY()).isEqualTo(EFFECTIVE_PIXEL_DIM);
	}

	@Test
	public void initialLevel_newInstance_returnsGridMapWithCorrectConcreteLayout() {
		//given
		final List<Concrete> concreteLayout = CONCRETE_LAYOUT;
		//then
		assertThat(concreteLayout).hasSize(154);
		assertThat(getCount(concreteLayout, GridObject::getX, 30)).isEqualTo(MAP_HEIGHT * 2);
		assertThat(getCount(concreteLayout, GridObject::getY, 12)).isEqualTo(MAP_WIDTH * 2);
	}

	private static long getCount(List<Concrete> concreteLayout, Function<Concrete, Integer> mapper, int posLimit) {
		return concreteLayout.stream()
				.map(mapper)
				.filter(position -> position == 0 || position == posLimit * EFFECTIVE_PIXEL_DIM)
				.count();
	}
}
