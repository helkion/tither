/**
 * 
 */
package nz.hmp.tither.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

/**
 * @author helcio
 *
 */
public class XmlUtils {
	
	public static String marshal(Object obj, Class<?> clazz) 
			throws JAXBException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		JAXBContext
				.newInstance(clazz)
				.createMarshaller()
				.marshal(obj, baos);
		
		byte[] bytes = baos.toByteArray();
		String xml = new String(bytes);
	
		return xml;
	}
	
	
	public static Object xmlToObject(String xml, Class<?> clazz) 
			throws JAXBException, UnsupportedEncodingException {

		Object obj = JAXBContext
				.newInstance(clazz)
    			.createUnmarshaller()
    			.unmarshal(new StreamSource(
    					new StringReader(xml)));
		return obj;
    }
	
	    

}
