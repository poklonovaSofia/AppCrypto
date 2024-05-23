package org.example.spring.repositories.main;

import org.example.spring.models.main.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
