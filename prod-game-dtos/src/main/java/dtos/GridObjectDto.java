package dtos;

import lombok.Setter;

import javax.xml.bind.annotation.*;

@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"position", "isConcreteCollision"})
public class GridObjectDto {

	@XmlElement(name = "Position", required = true)
	protected PositionDto position;
	@XmlAttribute(name = "ConcreteCollision")
	protected boolean isConcreteCollision;
}
