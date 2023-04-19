package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRankingRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<MemberRankingDto> findAllOrderedByRank() {

        return jdbcTemplate.query("select name, score, rank() over (order by score desc) as \'ranking\' from Member"
                , memberRankingDtoRowMapper());
    }

    private RowMapper<MemberRankingDto> memberRankingDtoRowMapper() {
        return (rs, rowNum) -> {
            MemberRankingDto memberRankingDto = new MemberRankingDto();
            memberRankingDto.setName(rs.getString("name"));
            memberRankingDto.setScore(rs.getLong("score"));
            memberRankingDto.setRanking(rs.getLong("ranking"));
            return memberRankingDto;
        };
    }

}
