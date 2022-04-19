package com.example;

import com.example.mongo.Lecturer;
import com.example.mongo.LecturerRepository;
import com.example.mongo.University;
import com.example.mongo.UniversityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class Controller {

    private final LecturerRepository lecturerRepository;
    private final UniversityRepository universityRepository;
    
    public Controller(LecturerRepository lecturerRepository, UniversityRepository universityRepository) {
        this.lecturerRepository = lecturerRepository;
        this.universityRepository = universityRepository;
    }

    @GetMapping
    public Flux<Lecturer> getLecturers() {
        return lecturerRepository.findAll();
    }

    @GetMapping("/universities")
    public Flux<University> getUniversities() {
        return universityRepository.findAll();
    }
    
    @PostMapping(value = "/lecturer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Lecturer> createLecturer(@RequestBody Lecturer lecturer) {
        return lecturerRepository.insert(lecturer);
    }

    @PostMapping("/university")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<University> createUniversity() {
        University university = new University();
        university.setName("Boring University");

        // insert only 1 university at most 
        return universityRepository.count()
                .flatMap(count -> count > 0 ? Mono.empty() : Mono.just(count))
                .flatMap(unused -> universityRepository.insert(university));
    }
    
    // This example shows combination of Mono&Flux into a new Flux with values from both
    @GetMapping("/combined")
    public Flux<CombinedDto> getCombined() {
        Flux<Lecturer> lecturerFlux = lecturerRepository.findAll();

        // retrieve one university from DB. More real life example could be that data is fetched from API
        Mono<University> university = universityRepository.findAll().next();

        return lecturerFlux
                .flatMap(lecturer -> Mono.just(lecturer).zipWith(university))
                .map(this::combine);
    }
    
    private CombinedDto combine(Tuple2<Lecturer, University> tuple) {
        CombinedDto dto = new CombinedDto();
        dto.setLecturer(tuple.getT1());
        dto.setUniversity(tuple.getT2());
        return dto;
    }
}
