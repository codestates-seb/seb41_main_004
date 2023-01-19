package com.codestates.azitserver.domain.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.codestates.azitserver.domain.common.Auditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuthNumber extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authNumberId;

	/**
	 * 인증번호 발송한 회원 email
	 */
	@Column(name = "MEMBER_EMAIL", nullable = false)
	private String email;

	/**
	 * 이메일로 발송한 인증 번호
	 */
	@Column(name = "AUTH_NUMBER", nullable = false)
	private String authNum;
}
