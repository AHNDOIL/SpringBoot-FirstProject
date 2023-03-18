insert into article(title,content) values ('user1','1111');
insert into article(title,content) values ('user2','2222');
insert into article(title,content) values ('user3','3333');

--article 더미 데이터
insert into article(title,content) values ('당신의 인생 영화는?','댓글');
insert into article(title,content) values ('당신의 맛집은?','댓글');
insert into article(title,content) values ('당신의 수면주기는?','댓글');

--comment 더미 데이터
insert into comment(article_id, nickname, body) values (4, '라이언','인셉션'); --ID값이 4번인 Article에 연결
insert into comment(article_id, nickname, body) values (5, '튜브','현풍'); --ID값이 5인 Article에 연결
insert into comment(article_id, nickname, body) values (6, '어피치','잠 좀 자자..'); --ID값이 6번인 Article에 연결


