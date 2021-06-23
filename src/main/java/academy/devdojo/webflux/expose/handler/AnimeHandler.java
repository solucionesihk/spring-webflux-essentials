package academy.devdojo.webflux.expose.handler;

import academy.devdojo.webflux.domain.Anime;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import academy.devdojo.webflux.exception.ErrorExceptionHandler;
import academy.devdojo.webflux.service.AnimeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

@Component
@RequiredArgsConstructor
public class AnimeHandler {

    @Autowired
	private AnimeService animeService;
    @Autowired
	private ErrorExceptionHandler errorExceptionHandler;
	
	public AnimeHandler (AnimeService animeService) {
		this.animeService = animeService;
	}
	
	public Mono<ServerResponse> getAnimeList(ServerRequest request){
		Flux<Anime> anime = animeService.findAll();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(anime, Anime.class);
	}
	
	public Mono<ServerResponse> getAnimeById (ServerRequest request){
		Mono<Integer> body = Mono.just(Integer.valueOf(request.pathVariable("id")));
		Mono<Anime> result = body.flatMap(animeService::findById);
		return result.flatMap(d->ServerResponse.ok().bodyValue(d));
	}
	
	public Mono<ServerResponse> newAnime(ServerRequest request){
		Mono<Anime> body = request.bodyToMono(Anime.class);
		Mono<Anime> result = body.flatMap(animeService::save);
		return result.flatMap(b->ServerResponse.ok().bodyValue(b))
				;
	}
	
	public Mono<ServerResponse> saveAllAnime(ServerRequest request){
                Flux<Anime> results = request.bodyToMono(Anime[].class)
                        .flatMapMany(Flux::fromArray)
                        .flatMap(b -> animeService.save(b));
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(results, Object.class);
	}
        
        public Mono<ServerResponse> updateAnime(ServerRequest request){
                Mono<Anime> body = request.bodyToMono(Anime.class);
                Mono<Object> result = body.flatMap(animeService::update);
		return result.flatMap(b->ServerResponse.ok().bodyValue(b));
	}
	
        public Mono<ServerResponse> deleteAnime (ServerRequest request){
		Mono<Integer> body = Mono.just(Integer.valueOf(request.pathVariable("id")));
                Mono<Object> result = body.flatMap(animeService::delete);
                return result.flatMap(b->ServerResponse.ok().bodyValue(b));
        }
        
}
