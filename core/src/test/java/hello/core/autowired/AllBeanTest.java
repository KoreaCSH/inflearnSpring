package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);

        int discountprice1 = discountService.discount(memberA, 10000, "rateDiscountPolicy");
        int discountprice2 = discountService.discount(memberA, 20000, "fixDiscountPolicy");
        Assertions.assertThat(discountprice1).isEqualTo(1000);
        Assertions.assertThat(discountprice2).isEqualTo(1000);
    }

    @Configuration
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        public int discount(Member member, int price, String discountPolicyCode) {

            DiscountPolicy discountPolicy = policyMap.get(discountPolicyCode);
            int discountprice = discountPolicy.discount(member, price);
            return discountprice;
        }
    }

}
