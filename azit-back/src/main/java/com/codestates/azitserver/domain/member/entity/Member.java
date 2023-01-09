package com.codestates.azitserver.domain.member.entity;

import com.codestates.azitserver.global.Audit.Auditable;


import javax.persistence.*;

@Entity
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

    private String password;
    private Gender gender;
    private int birthYear;
    private String aboutMe;
    private int reputation;
    private MemberStatus memberStatus;

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
