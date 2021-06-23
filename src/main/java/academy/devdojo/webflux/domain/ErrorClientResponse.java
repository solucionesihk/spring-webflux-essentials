package academy.devdojo.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorClientResponse {
	private String exceptionName;
	private String exceptionText;
}
