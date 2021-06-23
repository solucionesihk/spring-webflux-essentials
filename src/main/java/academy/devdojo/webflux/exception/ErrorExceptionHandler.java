package academy.devdojo.webflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.webflux.domain.ErrorClientResponse;
import reactor.core.publisher.Mono;

@Component
public class ErrorExceptionHandler {

	public Mono<ServerResponse> retrieveExceptionResponse(Throwable error) {
        if (error instanceof ResponseStatusException){
        	ResponseStatusException ex = (ResponseStatusException) error;
            if (ex.getStatus().value() == 204)  return ServerResponse.noContent().build();
            if (ex.getStatus().value() == 404)  return ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(retrieveErrorResponse(ex));
            if (ex.getStatus().value() == 400)  return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(retrieveErrorResponse(ex));
            if (ex.getStatus().value() == 401)  return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(retrieveGenericErrorResponse(error));
    }
	
	private ErrorClientResponse retrieveErrorResponse(ResponseStatusException ex) {
		return ErrorClientResponse.builder()
				.exceptionName(ex.getMessage())
				.exceptionText(ex.getReason())
				.build();
	}
	
	private ErrorClientResponse retrieveGenericErrorResponse(Throwable ex) {
		return ErrorClientResponse.builder()
				.exceptionName(ex.getMessage())
				.exceptionText(ex.toString())
				.build();
	}
	
}
