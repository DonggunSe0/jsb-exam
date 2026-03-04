package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id //primary key
//    기본키 값을 DB가 자동으로 만들어 주는 방식 사용
// IDENTITY 전략: MySQL/H2의 AUTO_INCREMENT처럼 insert 시 DB가 1,2,3... 증가값을 만들어줌
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //integer는 null이 될 수 있기 때문에 Integer로 선언
    //기본키는 null이 될 수 없지만, 객체가 생성될 때는 아직 DB에 저장되지 않았기 때문에 id가 null인 상태로 시작할 수 있음
    // 그래서 Integer로 선언하여 null이 될 수 있도록 함

//이 필드의 DB 컬럼 타입을 직접 지정
//여기서는 content를 DB에서 TEXT 타입으로 만들겠다는 뜻
    @Column(columnDefinition = "TEXT") //컬럼 옵션을 직접 지정하고 싶을 때 내가 타입을 변경하고싶어서
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
}
