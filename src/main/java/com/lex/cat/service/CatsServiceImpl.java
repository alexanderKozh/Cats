package com.lex.cat.service;

import com.lex.cat.model.Cat;
import com.lex.cat.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CatsServiceImpl implements CatService {

    private final CatRepository catRepository;

    @Autowired
    public CatsServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    @Transactional
    public List<Cat> getAll(Pageable pageable) {
        return catRepository.findAll(pageable).toList();
    }

    @Override
    @Transactional
    public void save(Cat cat) {
        catRepository.save(cat);
    }
}
