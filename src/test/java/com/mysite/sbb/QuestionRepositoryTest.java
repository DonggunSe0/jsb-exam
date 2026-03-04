package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test") // 테스트 실행 시 "test" 프로파일을 활성화하여 application-test.properties 파일을 사용하도록 지정
class QuestionRepositoryTest {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

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

	@Test
	@DisplayName("findBySubject")
	void t3() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?").get();
		//select * from question where subject = {subject} 와 같은 SQL이 실행됩니다.

		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t4() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.").get();
		assertEquals(1, q.getId());
	}
	//findBySubjectAndContent 메서드는 해당 subject와 content에 해당하는 Question 객체를 Optional로 반환합니다.
	//select * from question where subject = {subject} and content = {content} 와 같은 SQL이 실행됩니다.

	@Test
	@DisplayName("findBySubjectLike")
	void t5() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		//sql에서 like 연산자를 사용하여 subject가 "sbb"로 시작하는 Question 객체들을 검색합니다.
		//select * from question where subject like 'sbb%' 와 같은 SQL이 실행됩니다.
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("데이터수정")
	void t6() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");

		this.questionRepository.save(q);

		//검증로직
		Question q2 = this.questionRepository.findById(1).get();
		assertEquals("수정된 제목", q2.getSubject());
	}

	@Test
	@DisplayName("데이터삭제")
	void t7() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	@DisplayName("답변 데이터 저장 - repository 버전")
	void t8() {
		Question question = this.questionRepository.findById(2).get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(question);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	@DisplayName("답변 데이터 저장 - OneToMany 버전")
	@Transactional
	@Rollback(false) // 테스트 메서드에서 수행된 데이터 변경 작업을 롤백하지 않고 실제로 데이터베이스에 반영하도록 설정
	void t9() {
		Question question2 = this.questionRepository.findById(2).get();
		int beforeSize = question2.getAnswers().size();
		question2.addAnswer("네 자동으로 생성됩니다.");
		int afterSize = question2.getAnswers().size();

		assertEquals(beforeSize+1, afterSize);
	}

	@Test
	@DisplayName("2번 질문의 답변 찾기")
	//트랜잭션 => DB 작업의 단위
	@Transactional // => DB 작업이 하나의 트랜잭션으로 묶여서 실행되도록 설정
	void t10(){
		Question q2 = questionRepository.findById(2).get(); //repository에는 기본적으로 @Transactional이 적용되어있음 (문닫음)
//		System.out.println("q2.getSubject() = " + q2.getSubject());
		Answer answer = q2.getAnswers().get(0); // 영업시간 늘리기
		//answer에서 transactional이 없으면 영업시간이 끝나서 DB 연결이 끊어짐 => LazyInitializationException 발생
		//왜? Question 엔티티에서 Answer 엔티티를 참조할 때, @OneToMany 어노테이션의 fetch 속성이 LAZY로 설정되어 있기 때문

		System.out.println("answer = " + answer.getContent());
	}

}
