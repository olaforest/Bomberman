import dtos.GameContextDto;
import dtos.GridMapDto;
import dtos.PositionDto;
import dtos.gridObjects.ConcreteDto;
import dtos.gridObjects.ExitwayDto;
import dtos.gridObjects.animatedObjects.BombDto;
import dtos.gridObjects.animatedObjects.BombermanDto;
import dtos.gridObjects.animatedObjects.BrickDto;
import dtos.gridObjects.animatedObjects.enemies.BalloomDto;
import dtos.gridObjects.powerUps.SpeedDto;
import utils.JaxbUtils;

import static java.util.Collections.singletonList;

class Starter {

	public static void main(String[] args) {

		final PositionDto positionDto = new PositionDto(12, 35);

		final GridMapDto gridMapDto = GridMapDto.builder()
				.spawnTimer(657)
				.levelSpec(new int[]{3, 3, 0, 0, 0, 0, 0, 0, 1})
				.concreteLayout(singletonList(new ConcreteDto()))
				.bricks(singletonList(new BrickDto()))
				.bombs(singletonList(new BombDto()))
				.enemies(singletonList(new BalloomDto()))
				.exitway(new ExitwayDto())
				.powerUp(new SpeedDto())
				.bomberman(new BombermanDto())
				.build();

		final GameContextDto gameContextDto = GameContextDto.builder()
				.gameTime(1)
				.livesLeft(3)
				.score(500)
				.level(10)
				.actualLevel(9)
				.endGameEnemiesSpawned(false)
				.gridMap(gridMapDto)
				.build();

		final ConcreteDto concreteDto = new ConcreteDto();

//		concreteDto.setPosition(positionDto);
//		concreteDto.setConcreteCollision(false);

		JaxbUtils.marshall(gameContextDto);
	}
}
