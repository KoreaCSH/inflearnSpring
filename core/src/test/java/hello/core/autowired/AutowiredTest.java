package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }
    
    static class TestBean {

        // @Autowired 는 스프링 컨테이너에 등록된 스프링 빈이어야 동작한다.
        // 만약 스프링 빈이 아닌 객체를 자동 주입하려고 하면 Exception 이 발생한다.
        // 하지만, 스프링 빈이 아니더라도 의존 자동 주입을 동작하게 하는 세 가지 방법이 있다.

        // 1. required = false 로 하면 의존 자동 주입 대상이 스프링 빈이 아닐 때 setter 자체를 실행하지 않는다.

        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        // 2. @Nullable 애노테이션을 활용한다.
        // 의존 자동 주입 대상이 스프링 빈이 아니라면 null 이 담긴다.

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        // 3. Java 1.8의 Optional<T>를 사용한다.
        // 의존 자동 주입 대상이 스프링 빈이 아니라면 Optional.empty 가 담긴다.

        // 2, 3 방법은 생성자 주입에도 활용할 수 있다.

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }

    }

}
