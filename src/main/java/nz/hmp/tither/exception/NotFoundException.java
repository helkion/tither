/**
 * 
 */
package nz.hmp.tither.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 */
public class NotFoundException extends AppException {

	private static final long serialVersionUID = 7289148662055985221L;
	
	public NotFoundException() {
		super("Register not found!");
		this.httpStatus = HttpStatus.NOT_FOUND;
	}

}
