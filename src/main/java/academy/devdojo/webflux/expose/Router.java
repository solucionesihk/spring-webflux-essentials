package academy.devdojo.webflux.expose;

import academy.devdojo.webflux.expose.handler.AnimeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Router {

    	@Bean
	public RouterFunction<ServerResponse> AnimeEndpoint (AnimeHandler handler){
        return RouterFunctions.nest(path("/anime/v1"),
                RouterFunctions
                .route(GET("/"), handler::getAnimeList)
                .andRoute(GET("/{id}"), handler::getAnimeById)
                .andRoute(POST("/save"), handler::newAnime)
                .andRoute(POST("/saveAll"), handler::saveAllAnime)
                .andRoute(PUT("/"), handler::updateAnime)
                .andRoute(DELETE("/{id}"), handler::deleteAnime)
                );
    }
}
