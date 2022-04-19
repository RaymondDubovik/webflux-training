package com.example.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UniversityRepository extends ReactiveMongoRepository<University, String> {
}