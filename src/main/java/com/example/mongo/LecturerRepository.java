package com.example.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;  

public interface LecturerRepository extends ReactiveMongoRepository<Lecturer, String> {
}