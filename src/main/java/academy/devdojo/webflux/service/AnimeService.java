package academy.devdojo.webflux.service;

import java.util.List;

import academy.devdojo.webflux.domain.Anime;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface AnimeService {

    public Flux<Anime> findAll();

    public Mono<Anime> findById(int id);
    
    public Mono<Anime> save(Anime anime);

    public Flux<Anime> saveAll(List<Anime> animes);

    public Mono<Void> update(Anime anime);

    public Mono<Void> delete(int id);
}
