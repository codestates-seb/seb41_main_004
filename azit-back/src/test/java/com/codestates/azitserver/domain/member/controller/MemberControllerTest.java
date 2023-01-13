package com.codestates.azitserver.domain.member.controller;

import com.codestates.azitserver.domain.club.controller.ClubFieldDescriptor;
import com.codestates.azitserver.domain.member.dto.MemberDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.mapper.MemberMapper;
import com.codestates.azitserver.domain.member.service.MemberService;
import com.codestates.azitserver.domain.stub.MemberStubData;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.azitserver.global.utils.AsciiDocsUtils.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MemberController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper memberMapper;
    @Autowired
    private Gson gson;

    Member member;
    MemberDto.Post post;
    MemberDto.Patch patch;
    MemberDto.Response response;
    Page<Member> memberPage;

    @BeforeEach
    void beforeEach() {
        member = MemberStubData.stubMember();
        post = MemberStubData.stubMemberDtoPost();
        patch = MemberStubData.stubMemberDtoPatch();
        response = MemberStubData.stubMemberDtoResponse();
        memberPage = MemberStubData.stubMemberPage();
    }

    @Test
    void postMemberTest() throws Exception {
        // given
        given(memberMapper.memberPostDtoToMember(Mockito.any(MemberDto.Post.class)))
                .willReturn(member);
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(member);
        given(memberMapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

        String content = gson.toJson(post);
        // when
        ResultActions postActions =
                mockMvc.perform(
                        post("/api/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .header("Authorization", "Required JWT access token")
                );
        // then
        postActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.memberId").value(1))
                .andDo(getDefaultDocument(
                        "post-member",
                              requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
                              MemberFieldDescriptor.getPostRequestPartFieldsSnippet(),
                              MemberFieldDescriptor.getSingleResponseSnippet()
                        )
                );

    }
}
//
//    @Test
//    void getMemberTest() throws Exception {
//        // given
//        MemberDto.Post post = new MemberDto.Post("aswd@naver.com", "닉넴",
//                "12345678", "12345678",
//                Member.Gender.MALE,"1995", "자기소개");
//        String postContent = gson.toJson(post);
//
//
//        ResultActions postActions =
//                mockMvc.perform(
//                        post("/api/members")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(postContent)
//                );
//
//        long memberId = 1L;
//
//        // when / then
//
//        mockMvc.perform(
//                get("/api/members/"+memberId)
//                        .accept(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
//                .andExpect(jsonPath("$.data.nickname").value(post.getNickname()))
//                .andExpect(jsonPath("$.data.password").value(post.getPassword()))
//                .andExpect(jsonPath("$.data.passwordCheck").value(post.getPasswordCheck()))
//                .andExpect(jsonPath("$.data.gender").value(post.getGender()))
//                .andExpect(jsonPath("$.data.birthYear").value(post.getBirthYear()))
//                .andExpect(jsonPath("$.data.aboutMe").value(post.getAboutMe()));
//
//    }
//}
