package com.codestates.azitserver.domain.member.entity;

import com.codestates.azitserver.domain.common.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

@Entity
@Setter
@AllArgsConstructor
@Builder
@Getter
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true) //TODO FK
    private Long avatar_image_id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    //TODO 제약조건
    private String password;
    private Gender gender;
    private int birthYear;
    private String aboutMe;
    private int reputation;
    private MemberStatus memberStatus;

	// YH: Roles 필요해서 작성하였습니다. 확인 부탁드립니다!
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	public enum Gender {
		MALE("남자"),
		FEMALE("여자");

        private String gender;

        Gender(String gender) {
            this.gender = gender;
        }
    }

    public enum MemberStatus {
        ACTIVE("활성"),
        DELETED("탈퇴");

        private String memberStatus;

        MemberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
        }
    }



}
