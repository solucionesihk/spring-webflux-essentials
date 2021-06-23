package academy.devdojo.webflux.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErrorUtil{

	public static ResponseStatusException buildException(HttpStatus status, String exceptionText) {
		return new ResponseStatusException(status, exceptionText);
	}
	
	public static ResponseStatusException badRequestInvalidParameterException(String exceptionText) {
		return ErrorUtil.buildException(
				HttpStatus.valueOf(400), 
				exceptionText);
	}
	
	public static ResponseStatusException badRequestInvalidOperationException(String exceptionText) {
		return ErrorUtil.buildException(
				HttpStatus.valueOf(400), 
				exceptionText);
	}
	
	public static ResponseStatusException functionalErrorException(String exceptionText) {
		return ErrorUtil.buildException(
				HttpStatus.valueOf(406), 
				exceptionText);
	}
	
	public static ResponseStatusException malFormedResponseApiException(String exceptionText) {
		return ErrorUtil.buildException(
				HttpStatus.valueOf(500), 
				exceptionText);
	}
	
	public static ResponseStatusException serverErrorException(String exceptionText) {
		return ErrorUtil.buildException(
				HttpStatus.valueOf(500), 
				exceptionText);
	}

}