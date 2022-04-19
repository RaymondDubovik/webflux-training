package com.example;

import com.example.mongo.Lecturer;
import com.example.mongo.University;

public class CombinedDto {

    private University university;
    private Lecturer lecturer;

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}
