package com.back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // 테스트 실행 시 "test" 프로파일을 활성화하여 application-test.properties 파일을 사용하도록 지정
class JsbExamApplicationTests {

	@Test
	void contextLoads() {
	}

}
