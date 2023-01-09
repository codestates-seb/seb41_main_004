package com.codestates.azitserver.global.auth.userdetails;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.repository.MemberRepository;
import com.codestates.azitserver.global.auth.utils.CustomAuthorityUtils;
import com.codestates.azitserver.global.exception.BusinessLogicException;
import com.codestates.azitserver.global.exception.dto.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final CustomAuthorityUtils authorityUtils;

	// Email로 회원 찾아서 UserDetails 반환
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		Member findMember = optionalMember.orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return new MemberDetails(findMember);
	}

	// DB에서 Email 조회하고, 권한정보 생성
	private final class MemberDetails extends Member implements UserDetails {
		MemberDetails(Member member) {
			setMemberId(member.getMemberId());
			setEmail(member.getEmail());
			setPassword(member.getPassword());
			setRoles(member.getRoles());
			setMemberStatus(member.getMemberStatus());
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorityUtils.createAuthorities(this.getRoles());
		}

		@Override
		public String getUsername() {
			return getEmail();
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
			if (getMemberStatus() == MemberStatus.DELETED) {
				return false;
			}
			return true;
		}
	}
}