package com.codestates.azitserver.domain.member.controller;

import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.getRequestPreProcessor;
import static com.codestates.azitserver.global.utils.AsciiDocsUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private Gson gson;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("aswd@naver.com", "닉넴", "12345678", "12345678",
                    Member.Gender.MALE,"1995", "자기소개");
        String content = gson.toJson(post);

        given(memberMapper.memberPostDtoToMember(Mockito.any(MemberDto.Post.class)))
                .willReturn(new Member());

        Member mockResultMember = new Member();
        mockResultMember.setMemberId(1L);
        mockResultMember.setAvatar_image_id(1L);
        mockResultMember.setGender(Member.Gender.MALE);
        mockResultMember.setReputation(10);
        mockResultMember.setMemberStatus(Member.MemberStatus.ACTIVE);

        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(mockResultMember);

        // when
        ResultActions postActions =
        mockMvc.perform(
                post("/api/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
        // then
        postActions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/api/members/"))))
                .andDo(document(
                        "post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                )
                        )
                ))
        ;
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

        long memberId = 1L;

        // when / then

        mockMvc.perform(
                get("/api/members/"+memberId)
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
