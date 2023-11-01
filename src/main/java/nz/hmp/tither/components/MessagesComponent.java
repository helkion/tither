/**
 * 
 */
package nz.hmp.tither.components;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * @author helcio
 *
 */
@Component
public class MessagesComponent {

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(
				messageSource, new Locale("en", "NZ"));
	}

	public String get(String code) {
		return accessor.getMessage(code);
	}
	
	public String get(String code, Object... args) {
		return accessor.getMessage(code, args);
	}

}
