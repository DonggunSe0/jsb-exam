package com.back;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // 질문과 답변은 1:N 관계이므로, Question 엔티티에서 Answer 엔티티를참조할 때 @OneToMany 어노테이션을 사용
    //mappedBy 속성은 Answer 엔티티에서 Question 엔티티를 참조하는 필드 이름을 지정
    //Question_id, Answer_id 컬럼이 만들어지는 것을 방지하기 위해 mappedBy 속성을 사용하여 양방향 연관관계를 설정
    //cascade = CascadeType.REMOVE 옵션은 Question 엔티티가 삭제될 때, 해당 Question에 연결된 Answer 엔티티들도 함께 삭제되도록 설정
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers;
}
