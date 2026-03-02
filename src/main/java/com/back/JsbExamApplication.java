package com.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsbExamApplication {

	public static void main(String[] args) {
		//답글로 부터 질문 객체를 가져오는 방법
//		Answer a = new Answer();
//		a.getQuestion(); // Answer 객체에서 Question 객체를 가져오는 메서드 호출
//		Question question = a.getQuestion();  //
//		question.getContent(); //
//
//		a.getQuestion().getContent();
//		//질문을 조회하는 방법
//		Question q = new Question();
//		List<Answer> answers = q.getAnswers();


		SpringApplication.run(JsbExamApplication.class, args);
	}

}
