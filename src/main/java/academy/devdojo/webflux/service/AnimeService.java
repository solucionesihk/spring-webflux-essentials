package academy.devdojo.webflux.service;

import academy.devdojo.webflux.domain.Anime;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface AnimeService {

    public Flux<Anime> findAll();

    public Mono<Anime> findById(int id);
    
    public Mono<Anime> findByName (String name);
        
    public Mono<Anime> save(Anime anime);
    
    public Mono<Anime> saveValidationWhitoutException(Anime anime);

    public Mono<Void> update(Anime anime);

    public Mono<Void> delete(int id);
}
