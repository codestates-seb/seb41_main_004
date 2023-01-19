package com.codestates.azitserver.domain.member.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.codestates.azitserver.domain.member.entity.MemberReport;
import com.codestates.azitserver.domain.member.repository.MemberReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {
	private final MemberReportRepository memberReportRepository;

	public List<MemberReport> getAllMemberReport() { return memberReportRepository.findAll();}

	public void deleteMemberReportById(Long memberReportId) {
		memberReportRepository.deleteById(memberReportId);
	}
}
