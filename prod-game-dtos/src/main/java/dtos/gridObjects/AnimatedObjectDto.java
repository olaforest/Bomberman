package dtos.gridObjects;

import dtos.AnimationDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"animationList", "currentAnimation", "animationNumber", "counter", "animCycleParam"})
public class AnimatedObjectDto {

	@XmlElementWrapper(name = "animations", required = true)
	@XmlElement(name = "animation")
	protected List<AnimationDto> animationList;
	@XmlElement
	private AnimationDto currentAnimation;
	@XmlAttribute
	protected boolean isDead;
	@XmlAttribute
	protected boolean isObsolete;
	private int animationNumber;
	protected int counter;
	@XmlElement(name = "animationCycleParam")
	protected int animCycleParam;
}
