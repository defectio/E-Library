package com.defectio.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {

	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDao adminMemberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	/**
	 * 관리자 회원 가입 service
	 * @param adminMemberVo
	 * @return
	 */
	public int createAccountConfirm(AdminMemberVo adminMemberVo) {
		// 이미 등록된 관리자인지 체크
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id());
		
		/**
		 * isMember : false -> 등록되지 않은 관리자
		 */
		if (!isMember) {
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);
			
			if (result > 0) {
				return ADMIN_ACCOUNT_CREATE_SUCCESS;
			} else {
				return ADMIN_ACCOUNT_CREATE_FAIL;
			}
		} else {
			return ADMIN_ACCOUNT_ALREADY_EXIST;
		}
	}
	
	/**
	 * 로그인 시도하는 AdminMemberVo 객체 정보를 리턴함.
	 *  - 회원이면 AdminMemberVo 객체 정보 리턴
	 *  - 회원이 아니면 null 리턴
	 * @param adminMemberVo
	 * @return
	 */
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {
		AdminMemberVo loginedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo);
		
		if (loginedAdminMemberVo != null) {  // 로그인 성공
			System.out.println("ADMIN MEMBER LOGIN SUCCESS");
		} else {  // 로그인 실패
			System.out.println("ADMIN MEMBER LOGIN FAIL");
		}
		
		return loginedAdminMemberVo;
	}
	
	/**
	 * 일반 관리자 목록을 출력한다.
	 * @return
	 */
	public List<AdminMemberVo> listupAdmin() {
		return adminMemberDao.selectAdmins();
	}
	
	/**
	 * 슈퍼 관리자가 일반 관리자의 로그인 여부를 승인한다.
	 * @param a_m_no
	 */
	public void setAdminApproval(int a_m_no) {
//		int result = adminMemberDao.updateAdminAccount(a_m_no);
		adminMemberDao.updateAdminAccount(a_m_no);
	}
	
	/**
	 * 관리자 계정 정보를 수정한다.
	 * @param adminMemberVo
	 */
	public int modifyAccountConfirm(AdminMemberVo adminMemberVo) {
		return adminMemberDao.updateAdminAccount(adminMemberVo);
	}
	
	/**
	 * 수정된 관리자 계정 정보를 리턴한다.
	 */
	public AdminMemberVo getLoginedAdminMemberVo(int a_m_no) {
		return adminMemberDao.selectAdmin(a_m_no);
	}
	
	/**
	 * 입력한 정보와 일치하는 관리자가 있으면 새로운 password를 DB에 업데이트 한 후, 그 결과를 리턴한다.
	 *  - update 성공 : 1, update 실패 : 0
	 * @param admin
	 * @return
	 */
	public int findPasswordConfirm(AdminMemberVo adminMemberVo) {
		AdminMemberVo admin = adminMemberDao.selectAdmin(adminMemberVo.getA_m_id(), adminMemberVo.getA_m_name(),
				adminMemberVo.getA_m_mail());

		int result = 0;
		if (admin != null) { // 해당 관리자가 있으면
			// 새로운 비밀번호 생성
			String newPassword = createNewPassword();
			result = adminMemberDao.updatePassword(adminMemberVo.getA_m_id(), newPassword);
			
			if (result > 0) {
				sendNewPasswordByMail(adminMemberVo.getA_m_mail(), newPassword);
			}
		} else {  // 해당 관리자가 없으면
			// 로그인 페이지로 이동
		}
		return result;
	}
	
	/**
	 * 새로운 password를 8자리 난수로 생성한다.
	 * @return
	 */
	private String createNewPassword() {
		char[] chars = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

		StringBuffer stringBuffer = new StringBuffer();
		
		/**
		 * [SecureRandom]
		 *   - java.security.SecureRandom 패키지에 있는 클래스로 Random 보다 강력한 난수를 생성한다.
		 *   - SecureRandom은 Random을 상속한다.
		 */
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		
		// 8자리 난수 생성
		for (int i = 0; i < 8; i++) {
			// nextInt(int n) : 0~n 미만의 정수형 난수 리턴
			index = secureRandom.nextInt(length);

			// index 짝수면 대문자로, 홀수면 소문자로 String 생성
			if (index % 2 == 0) {
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			} else {
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}
		}
		
		return stringBuffer.toString();
	}
	
	/**
	 * 새로운 password를 메일로 전송한다.
	 * @param toMailAddr
	 * @param newPassword
	 */
	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		
		/**
		 * MimeMessagePreparator 익명 구현 객체
		 */
		javaMailSenderImpl.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				
				mimeMessageHelper.setTo(toMailAddr);  // 받는 메일 주소
				mimeMessageHelper.setSubject("[한국 도서관] 새 비밀번호 안내입니다.");
				mimeMessageHelper.setText("새 비밀번호 : " + newPassword, true);
			}
		});
		
	}
	
}
