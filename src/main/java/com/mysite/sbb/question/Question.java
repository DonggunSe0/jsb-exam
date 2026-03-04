package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    //cascade = CascadeType.PERSIST 옵션은 Question 엔티티가 저장될 때, 해당 Question에 연결된 Answer 엔티티들도 함께 저장되도록 설정
    //fetch = FetchType.EAGER 옵션은 Question 엔티티를 조회할 때, 해당 Question에 연결된 Answer 엔티티들도 함께 조회하도록 설정 18
    //fetch하니까 조인 쿼리가 실행되면서 Question과 연결된 Answer 엔티티들도 함께 조회됨
    //EAGER는 필요한 데이터를 미리 로딩 => 불필요한 경우에도 데이터를 다 가져온다.
    //LAZY는 필요한 데이터를 나중에 로딩 => 실제로 필요한 경우에만 데이터를 가져온다.
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY , cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Answer> answers =  new ArrayList<>();
    //Question 엔티티가 생성될 때, answers 필드는 빈 ArrayList로 초기화됩니다.
    //이렇게 하면 Question 객체가 생성될 때마다 answers 리스트가 항상 초기화되어 null이 되는 것을 방지할 수 있습니다.

    public void addAnswer(String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(this);
        answers.add(answer);
    }
}
