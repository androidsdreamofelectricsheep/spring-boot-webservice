package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스내 모든 필드의 getter메소드 자동생성
@NoArgsConstructor // 기본 생성자 자동 추가 - public Posts(){}
@Entity // 테이블과 링크될 클래스 - 카멜케이스를 스네이크케이스로 변환해서 매칭 e.g. SalesManager.java => sales_manager
public class Posts extends BaseTimeEntity {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성규칙 스프링 부트 2.0에서부터 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment됨
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 컬럼 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨 기본값외 추가 변경이 필요할 때 사용 e.g. 기본값인 VARCHAR(255)에서 500, 타입을 TEXT로 변경 등등
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;

    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}

/*
setter메소드가 없는 이유
자바빈 규약을 생각하면서 getter/setter를 무작정 생성하는 경우
해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확하게 구분할 수가 없어 차후 기능 변경 시 복잡해짐
따라서 Entity클래스에는 절대 setter메소드를 만들지 않고 해당 필드의 값 변경이 필요할 때 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야함
예를 들어 주문 취소 메소드를 만든다고 가정하면

// wrong
public class Order{
    public void setStatus(boolean status){
        this.status = status;
    }
}

public void 주문서비스의_취소이벤트 (){
    order.setStatus(false);
}

// right
public class Order{
    public void cancelOrder(){
        this.status = false;
    }
}

public void 주문서비스의_취소이벤트 (){
    order.cancelOrder()
}
 */