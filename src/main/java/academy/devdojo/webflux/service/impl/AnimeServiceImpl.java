package academy.devdojo.webflux.service.impl;

import io.netty.util.internal.StringUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return animeRepository.save(anime);
    }

    @Override
    @Transactional
    public Flux<Anime> saveAll(List<Anime> animes) {
        return animeRepository.saveAll(animes)
            .doOnNext(this::throwResponseStatusExceptionWhenEmptyName);
    }

    private void throwResponseStatusExceptionWhenEmptyName(Anime anime){
        if(StringUtil.isNullOrEmpty(anime.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Name");
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
}
