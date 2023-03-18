package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB가 해당 객체를 인식하게 함 (해당 객체로 테이블을 생성)
@AllArgsConstructor
@NoArgsConstructor // default 생성자 추가
@ToString
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동 생성 ex) 1, 2, 3, ...
    private Long id; // 대표값

    @Column // DB 테이블 구성
    private String title;

    @Column
    private String content;

    public void patch(Article article){ //수정 단계에서 테이블을 작성하지 않고 보내도 기존의 값으로 채워 넣음
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }

}


