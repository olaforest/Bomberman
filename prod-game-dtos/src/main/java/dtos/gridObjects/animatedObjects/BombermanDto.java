package dtos.gridObjects.animatedObjects;

import dtos.gridObjects.AnimatedObjectDto;
import dtos.gridObjects.PowerUpDto;

import java.util.List;


public class BombermanDto extends AnimatedObjectDto {

	private List<PowerUpDto> powerUpsAcquired;
	private int speed, bombsAvailable, bombsLeft;
	private int invincibilityTimer;

	private boolean canWallpass, canDetonateBombs, canBombpass, canFlamepass, isInvincible;
}
