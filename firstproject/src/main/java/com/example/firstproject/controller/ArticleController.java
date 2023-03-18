package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j //로깅
public class ArticleController {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String CreateArticle(ArticleForm form){

       //  System.out.println(form.toString()); -> 로깅으로 대체
        log.info(form.toString());

        // 1. Dto를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository가 엔티티를 DB에게 저장하게 함
        Article saved = articleRepository.save(article); //CrudRepository에 정의 되어 있는 메서드
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        //1: id로 데이터를 가져옴
        Optional<Article> articleOptional = articleRepository.findById(id);
        Article articleEntity = null;
        if (articleOptional.isPresent()) {
            articleEntity = articleOptional.get();
            // 조회한 엔티티를 사용하는 코드
        } else {
            // 예외 처리 등 필요한 로직을 구현
        }

        //2: 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        //3: 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        //1: 모든 Article을 가져온다
        List<Article> articleEntityList = articleRepository.findAll();

        //2: 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        //3: 뷰 페이지
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        log.info(model.toString());

        //뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update") //원래는 patch로 해야하지만 폼데이터가 지원하지 않아 임시로 post
    public String update(ArticleForm form){
        log.info(form.toString());

        //1: DTO를 Entity로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //2: Entity를 DB로 저장
        //2-1: DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);


        //2-2: 기존 데이터의 값을 수정
        if(target != null){
            articleRepository.save(articleEntity); //Entity가 DB로 갱신
        }

        //3: 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete") //원래는 DeleteMapping이지만 html 문제로 Getmapping
    public String delete(@PathVariable Long id, RedirectAttributes rttr){

        //1: 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);

        //2: 가져온 대상을 삭제한다
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "delete complete!!");
        }

        //3: 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}




