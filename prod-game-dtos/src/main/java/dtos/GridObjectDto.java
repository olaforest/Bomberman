package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"position", "isConcreteCollision"})
public class GridObjectDto {

	protected PositionDto position;
	protected boolean isConcreteCollision;
}
