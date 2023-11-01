/**
 * 
 */
package nz.hmp.tither.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author helcio
 *
 */
@ControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value = AppException.class)
	public ResponseEntity<AppException.AppExceptionInfo> appException(
			AppException e){
		
		return new ResponseEntity<AppException.AppExceptionInfo>(
				e.info(), e.getHttpStatus());
	}
}
