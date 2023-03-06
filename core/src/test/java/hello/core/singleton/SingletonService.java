package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
        // 생성자의 접근제어자를 private로 설정함으로써 getInstance() 로만 인스턴스를 얻을 수 있도록 한다.
        // 이때 instance는 static 변수이므로 유일한 인스턴스이다.
        // 해당 로직으로 싱글톤 패턴을 적용할 수 있다.
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
