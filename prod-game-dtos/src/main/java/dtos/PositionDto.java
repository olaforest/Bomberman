package dtos;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"x", "y"})
public class PositionDto {
	@XmlElement(name = "X")
	private int x;
	@XmlElement(name = "Y")
	private int y;
}
