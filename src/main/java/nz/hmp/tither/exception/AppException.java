package nz.hmp.tither.exception;

import org.springframework.http.HttpStatus;

public class AppException extends Exception {
	private static final long serialVersionUID = 8657504538327621791L;
	
	protected HttpStatus httpStatus;
	protected String breadCrumbId;

	public AppException() {
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}
	
	public AppException(
			String message, 
			HttpStatus httpStatus,
			String breadCrumbId) {
		super(message);
		this.breadCrumbId = breadCrumbId;
		this.httpStatus = httpStatus;
	}
	
	public AppException(
			Throwable cause, 
			String breadCrumbId) {
		super(cause);
		this.breadCrumbId = breadCrumbId;
	}
	
	public AppException(
			Throwable cause, 
			HttpStatus httpStatus, 
			String breadCrumbId) {
		this(cause, breadCrumbId);
		this.httpStatus = httpStatus;
	}
	
	public AppExceptionInfo info() {
		return new AppExceptionInfo(
				this,
				httpStatus,
				breadCrumbId);
	}
	
	public class AppExceptionInfo {
		protected String cause;
		protected HttpStatus httpStatus;
		protected String breadCrumbId;
		
		public AppExceptionInfo(
				Throwable cause,
				HttpStatus httpStatus,
				String breadCrumbId) {
			
			this.cause = cause.getMessage();
			this.httpStatus = httpStatus;
			this.breadCrumbId = breadCrumbId;
		}

		public String getCause() {
			return cause;
		}

		public void setCause(String cause) {
			this.cause = cause;
		}

		public HttpStatus getHttpStatus() {
			return httpStatus;
		}

		public void setHttpStatus(HttpStatus httpStatus) {
			this.httpStatus = httpStatus;
		}

		public String getBreadCrumbId() {
			return breadCrumbId;
		}

		public void setBreadCrumbId(String breadCrumbId) {
			this.breadCrumbId = breadCrumbId;
		}
	}

	public String getBreadCrumbId() {
		return breadCrumbId;
	}

	public void setBreadCrumbId(String breadCrumbId) {
		this.breadCrumbId = breadCrumbId;
	}

	/**
	 * @return the httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
