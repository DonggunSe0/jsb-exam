package com.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test") // 테스트 실행 시 "test" 프로파일을 활성화하여 application-test.properties 파일을 사용하도록 지정
class QuestionRepositoryTest {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("findAll")
	void t1() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("findById")
	void t2() {
		Optional<Question> oq = this.questionRepository.findById(1);
		//Optional은 null이 될 수 있는 객체를 감싸는 래퍼 클래스입니다.
		//findById 메서드는 해당 ID에 해당하는 Question 객체를 Optional로 반환합니다.
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

}
