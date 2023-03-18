package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> { //Article의 id 타입

    @Override
    ArrayList<Article> findAll(); //Iterable<Article> findALL을 오버라이딩
}






