package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberRankingDto;
import hello.hellospring.repository.MemberRankingRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberRankingRepository memberRankingRepository;

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


//        try{
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }


        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }

    @Test
    void 랭킹_조회() {
        // given
        Member member1 = new Member();
        member1.setName("test1");
        member1.setScore(10);

        Member member2 = new Member();
        member2.setName("test2");
        member2.setScore(20);

        Member member3 = new Member();
        member3.setName("test3");
        member3.setScore(15);

        Member member4 = new Member();
        member4.setName("test4");
        member4.setScore(40);

        Member member5 = new Member();
        member5.setName("test5");
        member5.setScore(0);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);
        memberService.join(member4);
        memberService.join(member5);

        // when
        List<MemberRankingDto> allOrderedByRank = memberRankingRepository.findAllOrderedByRank();
        allOrderedByRank.stream().forEach(System.out::println);

        // then
    }
}