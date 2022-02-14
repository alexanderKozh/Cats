package com.lex.cat.repository;

import com.lex.cat.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, String> {
}