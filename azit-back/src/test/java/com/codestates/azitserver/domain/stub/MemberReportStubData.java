package com.codestates.azitserver.domain.stub;

import java.util.List;

import com.codestates.azitserver.domain.member.dto.MemberReportDto;
import com.codestates.azitserver.domain.member.entity.Member;
import com.codestates.azitserver.domain.member.entity.MemberMemberReport;
import com.codestates.azitserver.domain.member.entity.MemberReport;

public class MemberReportStubData {
	public static MemberReport stubMemberReport() {
		MemberReport memberReport = tempStubMemberReport();
		Member reporter = reporter();
		Member reportee = reportee();
		memberReport.setMemberMemberReportList(
			List.of(tempMemberMemberReport(1L, reporter, reportee))
		);
		return memberReport;
	}

	public static MemberReport tempStubMemberReport() {
		MemberReport tempMemberReport = new MemberReport();
		tempMemberReport.setReportId(1L);
		tempMemberReport.setReporterId(4L);
		tempMemberReport.setReporteeId(5L);
		tempMemberReport.setReportCategory(MemberReport.ReportCategory.ADVERTISE);
		tempMemberReport.setReportReason("불법도박광고했어요");

		return tempMemberReport;
	}

	public static MemberMemberReport tempMemberMemberReport(Long memberMemberReportId,
		Member reporter, Member reportee) {
		MemberMemberReport memberMemberReport =
			MemberMemberReport.builder()
				.memberMemberReportId(memberMemberReportId)
				.reporter(reporter)
				.reportee(reportee)
				.build();
		return memberMemberReport;
	}

	public static MemberReportDto.Post stubMemberReportDtoPost() {
		MemberReportDto.Post post = new MemberReportDto.Post();

		post.setReportCategory(MemberReport.ReportCategory.ADVERTISE);
		post.setReportReason("불법도박광고했어요");

		return post;
	}

	public static MemberReportDto.Response stubMemberReportDtoResponse() {
		MemberReportDto.Response response = new MemberReportDto.Response();

		response.setReportId(1L);
		response.setReportCategory(MemberReport.ReportCategory.ADVERTISE);
		response.setReportReason("불법도박광고했어요");

		return response;
	}

	public static Member reporter() {
		Member tempStubMember = Member.builder()
			.memberId(4L)
			.fileInfo(FileInfoStubData.getDefaultFileInfo())
			.email("reporter@naver.com")
			.nickname("신고쟁이")
			.password("123456@asdf")
			.gender(Member.Gender.MALE)
			.birthYear("2001")
			.aboutMe("일단신고함")
			.reputation(10)
			.memberStatus(Member.MemberStatus.ACTIVE)
			.build();

		return tempStubMember;
	}

	public static Member reportee() {
		Member tempStubMember = Member.builder()
			.memberId(5L)
			.fileInfo(FileInfoStubData.getDefaultFileInfo())
			.email("reportee@naver.com")
			.nickname("홍보쟁이")
			.password("123456@asdf")
			.gender(Member.Gender.MALE)
			.birthYear("1999")
			.aboutMe("일단홍보함")
			.reputation(10)
			.memberStatus(Member.MemberStatus.ACTIVE)
			.build();

		return tempStubMember;
	}
}
