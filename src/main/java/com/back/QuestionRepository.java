package com.back;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional <Question> findBySubject(String subject);
    //optional은 null이 될 수 있는 객체를 감싸는 래퍼 클래스입니다.
    //findBySubject 메서드는 해당 subject에 해당하는 Question 객체를 Optional로 반환

    Optional <Question> findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
}
