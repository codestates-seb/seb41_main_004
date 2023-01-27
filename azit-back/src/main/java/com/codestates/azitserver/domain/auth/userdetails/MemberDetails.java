package com.codestates.azitserver.domain.auth.userdetails;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.codestates.azitserver.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberDetails implements UserDetails, OAuth2User {
	private final Member member;
	private Map<String, Object> attributes;

	// 일반 로그인
	public MemberDetails(Member member) {
		this.member = member;
	}

	// oauth2 로그인
	public MemberDetails(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return member.getRoles().stream()
			.map(role -> (GrantedAuthority)() -> "ROLE_" + role)
			.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (member.getMemberStatus() == Member.MemberStatus.DELETED) {
			return false;
		}
		return true;
	}

	// oauth2 user
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	// oauth2 user
	@Override
	public String getName() {
		return member.getEmail();
	}
}
