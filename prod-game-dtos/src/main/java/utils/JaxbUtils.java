package utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

public class JaxbUtils {

	public static <T> void marshall(T object) {
		try {
			final JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
			marshaller.marshal(object, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
