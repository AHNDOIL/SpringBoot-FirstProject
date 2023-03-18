package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //해당 클래스를 서비스로 인식하여 스프링 부트에 서비스 객체 등록
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null)
            return null;

        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {

        Article article = dto.toEntity();

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || id != article.getId()) {
            return null;
        }

        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {

        Article target = articleRepository.findById(id).orElse(null);

        if(target == null)
            return null;

        articleRepository.delete(target);
        return target;
    }

    @Transactional //해당 메소드를 트랜젝션으로 묶음
    public List<Article> createArticles(List<ArticleForm> dtos) {

        //dto list를 Entity list로 변환
        List<Article> articleList = dtos.stream()
                                        .map(dto->dto.toEntity())
                                        .collect(Collectors.toList());
        //Entity list를 DB로 저장
        articleList.stream()
                   .forEach(article -> articleRepository.save(article));

        //강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        //결과값 반환
        return articleList;
    }
}

//        List<Article> articleList = new ArrayList<>();
//        for(int i=0; i<dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

//        for(int i=0; i<articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }
