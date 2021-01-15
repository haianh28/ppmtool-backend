package com.example.demo.repository;

import com.example.demo.domain.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {
    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);
    Project findByProjectIdentifier(String id);

    @Query("SELECT p FROM Project p WHERE p.id = :id")
    Project findByProjectId(@Param("id") Long id);
}
