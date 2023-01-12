package com.codestates.azitserver.domain.member.controller;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("aswd@naver.com", "닉넴", "12345678", "12345678",
                    Member.Gender.MALE,"1995", "자기소개");
        Member member = memberMapper.memberPostDtoToMember(post);
        member.setMemberId(1L);
        member.setAvatar_image_id(1L);
        member.setGender(Member.Gender.MALE);
        member.setReputation(10);
        member.setMemberStatus(Member.MemberStatus.ACTIVE);


        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(member);

        String content = gson.toJson(post);


        // when
        ResultActions actions =
        mockMvc.perform(
                post("/api/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("api/members/"))));
    }

    @Test
    void getMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("aswd@naver.com", "닉넴",
                "12345678", "12345678",
                Member.Gender.MALE,"1995", "자기소개");
        String postContent = gson.toJson(post);


        ResultActions postActions =
                mockMvc.perform(
                        post("/api/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postContent)
                );

        String location = postActions.andReturn().getResponse().getHeader("Location");

        // when / then
        mockMvc.perform(
                get(location)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(post.getNickname()))
                .andExpect(jsonPath("$.data.password").value(post.getPassword()))
                .andExpect(jsonPath("$.data.passwordCheck").value(post.getPasswordCheck()))
                .andExpect(jsonPath("$.data.gender").value(post.getGender()))
                .andExpect(jsonPath("$.data.birthYear").value(post.getBirthYear()))
                .andExpect(jsonPath("$.data.aboutMe").value(post.getAboutMe()));

    }
}
