package dtos;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"position", "isConcreteCollision"})
public class GridObjectDto {

	@XmlElement(required = true)
	protected PositionDto position;
	@XmlAttribute(name = "concreteCollision")
	protected boolean isConcreteCollision;
}
