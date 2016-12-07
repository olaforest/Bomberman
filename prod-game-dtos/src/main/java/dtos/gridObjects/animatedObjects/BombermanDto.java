package dtos.gridObjects.animatedObjects;

import dtos.gridObjects.AnimatedObjectDto;
import dtos.gridObjects.PowerUpDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class BombermanDto extends AnimatedObjectDto {

	@XmlElementWrapper(name = "powerUpsAcquired")
	@XmlElement(name = "powerUp")
	private List<PowerUpDto> powerUpsAcquired;
	private int speed;
	private int bombsAvailable;
	private int bombsLeft;
	private int invincibilityTimer;

	@XmlAttribute
	private boolean canWallpass;
	@XmlAttribute
	private boolean canDetonateBombs;
	@XmlAttribute
	private boolean canBombpass;
	@XmlAttribute
	private boolean canFlamepass;
	@XmlAttribute
	private boolean isInvincible;
}
