package com.codestates.azitserver.domain.member.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.domain.member.dto.MemberReportDto;
import com.codestates.azitserver.domain.member.entity.MemberReport;
import com.codestates.azitserver.domain.member.mapper.MemberReportMapper;
import com.codestates.azitserver.domain.member.service.MemberMemberReportService;
import com.codestates.azitserver.domain.member.service.MemberReportService;
import com.codestates.azitserver.global.dto.SingleResponseDto;

@RestController
@Validated
@RequestMapping(value = "/api/members/reports", produces = {MediaType.APPLICATION_JSON_VALUE})
public class MemberReportController {

	private final MemberReportService memberReportService;

	private final MemberReportMapper memberReportMapper;


	public MemberReportController(
		MemberReportService memberReportService,
		MemberReportMapper memberReportMapper
	) {
		this.memberReportService = memberReportService;
		this.memberReportMapper = memberReportMapper;
	}

	// 신고 생성
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> postMemberReport(@RequestBody @Valid MemberReportDto.Post reportPostDto) {

		// TODO 중복 신고 불가한지 확인
		// memberReportService.verifyExistingReport(reportPostDto.getReporterId(), reportPostDto.getReporterId())

		MemberReport tempMemberReport = memberReportMapper.reportPostDtoToReport(reportPostDto);
		MemberReportDto.Response response = memberReportMapper.reportToReportResponseDto(tempMemberReport);
		memberReportService.createMemberReport(tempMemberReport);
		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAllReport() {
		List<MemberReport> memberReportList = memberReportService.getAllMemberReport();
		List<MemberReportDto.Response> responseList = memberReportMapper.reportToReportResponseDto(memberReportList);

		return new ResponseEntity<>(new SingleResponseDto<>(responseList), HttpStatus.OK);
	}

	// TODO 특정 회원의 신고내역 확인하는 api 필요한지

	// TODO 특정 회원을 신고한 내역 확인 api 필요한지

	@DeleteMapping(value = {"/memberReport-id"})
	public ResponseEntity deleteMemberReport(@Positive @PathVariable("memberReport-id") Long memberReportId) {
		memberReportService.deleteMemberReportById(memberReportId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}
}


