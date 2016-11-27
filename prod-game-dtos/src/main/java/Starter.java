import dtos.GameContextDto;
import dtos.GridMapDto;
import dtos.GridObjects.ConcreteDto;
import utils.JaxbUtils;

import static java.util.Collections.singletonList;

public class Starter {

	public static void main(String[] args) {

		final GameContextDto gameContextDto = new GameContextDto();
		gameContextDto.setGameTime(1);
		gameContextDto.setLivesLeft(3);
		gameContextDto.setScore(500);
		gameContextDto.setLevel(10);
		gameContextDto.setActualLevel(9);
		gameContextDto.setEndGameEnemiesSpawned(false);

		final GridMapDto gridMapDto = new GridMapDto();
		gameContextDto.setGridMap(gridMapDto);

		gridMapDto.setSpawnTimer(123);
		gridMapDto.setLevelSpec(new int[] {3, 3, 0, 0, 0, 0, 0, 0, 1});
		gridMapDto.setConcreteLayout(singletonList(new ConcreteDto()));

		JaxbUtils.marshall(gameContextDto);
	}
}
