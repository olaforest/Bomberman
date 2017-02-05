package gameplayModel;

import gameplayModel.gridObjects.Concrete;
import gameplayModel.gridObjects.animatedObjects.Bomberman;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Predicate;

import static gameplayModel.GridMap.MAPHEIGHT;
import static gameplayModel.GridMap.MAPWIDTH;
import static gameplayModel.LevelManager.importLevels;
import static gameplayView.ImageManager.EFFECTIVE_PIXEL_DIMENSION;
import static org.assertj.core.api.Assertions.assertThat;

public class GridMapTest {

	@Test
	public void initialLevel_newInstance_returnsGridMapWithBombermanAtInitialPosition() {
		//given
		final Level level = importLevels().get(0);
		//when
		final Bomberman bomberman = new GridMap(level).getBomberman();
		//then
		assertThat(bomberman.getX()).isEqualTo(EFFECTIVE_PIXEL_DIMENSION);
		assertThat(bomberman.getY()).isEqualTo(EFFECTIVE_PIXEL_DIMENSION);
	}

	@Test
	public void initialLevel_newInstance_returnsGridMapWithCorrectConcreteLayout() {
		//given
		final List<Concrete> concreteLayout = GridMap.CONCRETE_LAYOUT;
		//then
		assertThat(concreteLayout).hasSize(154);
		assertThat(getCount(concreteLayout, block -> block.getX() == 0)).isEqualTo(MAPHEIGHT);
		assertThat(getCount(concreteLayout, block -> block.getX() == 30 * EFFECTIVE_PIXEL_DIMENSION)).isEqualTo(MAPHEIGHT);
		assertThat(getCount(concreteLayout, block -> block.getY() == 0)).isEqualTo(MAPWIDTH);
		assertThat(getCount(concreteLayout, block -> block.getY() == 12 * EFFECTIVE_PIXEL_DIMENSION)).isEqualTo(MAPWIDTH);
	}

	private static long getCount(List<Concrete> concreteLayout, Predicate<Concrete> condition) {
		return concreteLayout.stream()
					.filter(condition)
					.count();
	}
}
