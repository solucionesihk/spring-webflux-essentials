package academy.devdojo.webflux.service.impl;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.webflux.domain.Anime;
import academy.devdojo.webflux.repository.AnimeRepository;
import academy.devdojo.webflux.service.AnimeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public Flux<Anime> findAll() {
        return animeRepository.findAll();
    }

    @Override
    public Mono<Anime> findById(int id) {
        return animeRepository.findById(id);
    }

    @Override
    public Mono<Anime> save(Anime anime) {
        return this.findByName(anime.getName())
                .doOnNext(this::validateExisting)
                .switchIfEmpty(Mono.just(anime))
                .doOnNext(this::throwResponseStatusExceptionWhenEmptyName)
                .flatMap(animeRepository::save);
    }
    
    @Override
    public Mono<Anime> saveValidationWhitoutException(Anime anime) {
        return this.findByName(anime.getName())
                .filter(a->a.getName()!=anime.getName())
                .defaultIfEmpty(anime)
                .filter(a->!a.getName().isBlank() && !a.getName().isEmpty())
                .flatMap(animeRepository::save);
    }
    
    private void throwResponseStatusExceptionWhenEmptyName(Anime anime){
        if(StringUtil.isNullOrEmpty(anime.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not null or empty, please invalid Name");
        }
    }
    
    private void validateExisting(Anime anime){
        Mono<Anime> animes = animeRepository.findFirstByName(anime.getName());
        if(animes!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data encontrada en la BD");
        }
    }

    @Override
    public Mono<Void> update(Anime anime) {
        return findById(anime.getId())
            .flatMap(animeRepository::save)
            .then();
    }
    
    @Override
    public Mono<Void> delete(int id) {
        return findById(id)
            .flatMap(animeRepository::delete);
    }

    @Override
    public Mono<Anime> findByName(String name) {
        return animeRepository.findFirstByName(name);
    }
}
