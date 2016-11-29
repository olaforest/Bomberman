package dtos.gridObjects;

import dtos.AnimationDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AnimatedObjectDto {

	protected List<AnimationDto> animationList;
	private AnimationDto currentAnimation;
	protected boolean isDead, isObsolete;
	private int animationNumber;
	protected int counter, animCycleParam;
}
