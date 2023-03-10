package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        System.out.println("PrototypeBean1 = " + bean1);
        System.out.println("PrototypeBean2 = " + bean2);
        Assertions.assertThat(bean1).isNotSameAs(bean2);

        // 종료 메서드가 호출되지 않으므로 클라이언트가 프로토타입의 빈과 종료 메서드를 직접 관리해야 한다.
        bean1.destroy();
        bean2.destroy();

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("Prototype.init");
        }

        // prototype Bean은 스프링 컨테이너가 프로토타입 빈을 생성 -> 의존관계 주입 -> 초기화까지만 처리한다.
        // 그러므로 prototype Bean은 초기화 이후에 스프링 컨테이너의 스프링 빈 저장소에 남아있지 않기 때문에
        // @PostDestroy 를 호출할 수 없다.
        // 또한, 스프링 빈 저장소에 남아있지 않기 때문에 호출할 때마다 새로운 객체가 반환된다.
        @PreDestroy
        public void destroy() {
            System.out.println("Prototype.destroy");
        }

    }

}
