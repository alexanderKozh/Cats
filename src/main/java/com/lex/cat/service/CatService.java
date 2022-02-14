package com.lex.cat.service;

import com.lex.cat.model.Cat;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatService {

    List<Cat> getAll(Pageable pageable);

    void save(Cat cat);
}
