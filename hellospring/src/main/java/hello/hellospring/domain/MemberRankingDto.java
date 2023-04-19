package hello.hellospring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberRankingDto {

    private String name;
    private long score;
    private long ranking;

}
