package zoo.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import zoo.jpashop.domain.Member;
import zoo.jpashop.repository.MemberRepository;


@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("1000zoo");

        // when
        Long savedId = memberService.join(member);

        // then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member = new Member();
        member.setName("1000zoo");

        Member newMember = new Member();
        newMember.setName("1000zoo");

        // when
        Long savedId = memberService.join(member);

        // then
        assertThrows(
                IllegalStateException.class,
                () -> memberService.join(newMember)
        );

    }
}