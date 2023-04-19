package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberRankingDto;
import hello.hellospring.repository.MemberRankingRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRankingRepository memberRankingRepository;


    // 회원가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public long updateScore(Long memberId, long score) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다"));

        findMember.changeScore(score);
        return findMember.getId();
    }

    public List<MemberRankingDto> findAllRanking() {
        return memberRankingRepository.findAllOrderedByRank();
    }

}
