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
	@XmlElement(name = "currentAnimation")
	private AnimationDto currentAnimation;
	@XmlAttribute(name = "isDead")
	protected boolean isDead;
	@XmlAttribute(name = "isObsolete")
	protected boolean isObsolete;
	@XmlElement(name = "animationNumber")
	private int animationNumber;
	@XmlElement(name = "counter")
	protected int counter;
	@XmlElement(name = "animationCycleParam")
	protected int animCycleParam;
}
