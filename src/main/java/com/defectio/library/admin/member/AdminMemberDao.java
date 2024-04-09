package com.defectio.library.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminMemberDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * 중복 아이디 체크
	 * @param a_m_id
	 * @return
	 */
	public boolean isAdminMember(String a_m_id) {
		String sql = "SELECT COUNT(*) FROM tbl_admin_member "
				+ "WHERE a_m_id = ?";
	
		/**
		 * [queryForObject 매개변수]
		 * 	 - sql : SQL문
		 *   - Integer.class : 쿼리 실행 후 반환되는 데이터 타입
		 *   - a_m_id : 관리자가 입력한 아이디 
		 */
		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);
		
		return result > 0 ? true : false;
	}
	
	/**
	 * DB에 관리자 정보를 저장한다.
	 * @param adminMemberVo
	 * @return
	 */
	public int insertAdminAccount(AdminMemberVo adminMemberVo) {
		
		List<String> args = new ArrayList<String>();
		
		String sql =  "INSERT INTO tbl_admin_member(";
			   if (adminMemberVo.getA_m_id().equals("super_admin")) {
				   sql += "a_m_approval, ";
				   args.add("1");
			   }
			   
			   sql += "a_m_id, ";
			   args.add(adminMemberVo.getA_m_id());
			   
			   sql += "a_m_pw, ";
//			   args.add(adminMemberVo.getA_m_pw());
			   args.add(passwordEncoder.encode(adminMemberVo.getA_m_pw()));
			   
			   sql += "a_m_name, ";
			   args.add(adminMemberVo.getA_m_name());
			   
			   sql += "a_m_gender, ";
			   args.add(adminMemberVo.getA_m_gender());
			   
			   sql += "a_m_part, ";
			   args.add(adminMemberVo.getA_m_part());
			   
			   sql += "a_m_position, ";
			   args.add(adminMemberVo.getA_m_position());
			   
			   sql += "a_m_mail, ";
			   args.add(adminMemberVo.getA_m_mail());
			   
			   sql += "a_m_phone, ";
			   args.add(adminMemberVo.getA_m_phone());
			   
			   sql += "a_m_reg_date, a_m_mod_date) ";
			   
			   if (adminMemberVo.getA_m_id().equals("super_admin")) 
				   sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
			   else 
				   sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
			   
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 로그인 시 관리자 정보의 일치여부를 확인한다.
	 * @param adminMemberVo
	 * @return
	 */
	public AdminMemberVo selectAdmin(AdminMemberVo adminMemberVo) {
		String sql =  "SELECT * FROM tbl_admin_member "
					+ "WHERE a_m_id = ? AND a_m_approval > 0";
	
		// ID 중복체크 기능이 없어서 리스트로 adminMember를 담음
		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
		try {
			/**
			 * 파라미터
			 *   - sql : 쿼리문
			 *   - new RowMapper<AdminMemberVo>() {} : 익명 구현 객체(데이터베이스의 row를 매핑)
			 *   - adminMemberVo.getA_m_id() : 쿼리문 조회시 adminMemberVo 객체의 id 이용(쿼리문의 ?에 들어가는 값)
			 */
			adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo adminMemberVo = new AdminMemberVo();
					
					adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVo.setA_m_id(rs.getString("a_m_id"));
					adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVo.setA_m_name(rs.getString("a_m_name"));
					adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVo.setA_m_part(rs.getString("a_m_part"));
					adminMemberVo.setA_m_position(rs.getString("a_m_position"));
					adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return adminMemberVo;
				}
			}, adminMemberVo.getA_m_id());
			
			// pw 일치 검사( client pw, DB pw 비교 -> 일치하지 않으면 list clear
			if (!passwordEncoder.matches(adminMemberVo.getA_m_pw(), adminMemberVos.get(0).getA_m_pw())) {
				adminMemberVos.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
	}
	
	/**
	 * 모든 슈퍼, 일반 관리자 목록을 리턴한다.
	 * @return
	 */
	public List<AdminMemberVo> selectAdmins() {
		String sql =  "SELECT * FROM tbl_admin_member";
	
		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();

		try {
			adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo adminMemberVo = new AdminMemberVo();
					
					adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVo.setA_m_id(rs.getString("a_m_id"));
					adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVo.setA_m_name(rs.getString("a_m_name"));
					adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVo.setA_m_part(rs.getString("a_m_part"));
					adminMemberVo.setA_m_position(rs.getString("a_m_position"));
					adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return adminMemberVo;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adminMemberVos;
	}
	
	/**
	 * 슈퍼 관리자가 일반 관리자의 로그인 여부를 승인한다.
	 * @param a_m_no
	 * @return
	 */
	public int updateAdminAccount(int a_m_no) {
		String sql =  "UPDATE tbl_admin_member SET "
					+ "a_m_approval = 1 "
					+ "WHERE a_m_no = ?";
		int result = -1;
		try {
			result = jdbcTemplate.update(sql, a_m_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	/**
	 * 관리자 정보를 update 한다.
	 * @param adminMemberVo
	 * @return
	 */
	public int updateAdminAccount(AdminMemberVo adminMemberVo) {
		String sql = "UPDATE tbl_admin_member "
				+ "SET a_m_name = ?, a_m_gender = ?, a_m_part = ?, a_m_position = ?, a_m_mail = ?, a_m_phone = ?, a_m_mod_date = NOW() "
				+ "WHERE a_m_no = ?";
		
		int result = -1;
		
		Object[] args = new Object[] {adminMemberVo.getA_m_name(), 
				adminMemberVo.getA_m_gender(), 
				adminMemberVo.getA_m_part(), 
				adminMemberVo.getA_m_position(),
				adminMemberVo.getA_m_mail(),
				adminMemberVo.getA_m_phone(),
				adminMemberVo.getA_m_no()};
		
		// DB update 성공 : 1, 실패 : 0이 result에 담김
		result = jdbcTemplate.update(sql, args);
		return result;
	}
	
	/**
	 * 관리자 정보를 리턴한다.
	 */
	public AdminMemberVo selectAdmin(int a_m_no) {
		String sql = "SELECT * FROM tbl_admin_member WHERE a_m_no = ?";
		
		return jdbcTemplate.queryForObject(sql, new RowMapper<AdminMemberVo> () {
			@Override
			public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminMemberVo admin = new AdminMemberVo();
				
				admin.setA_m_no(rs.getInt("a_m_no"));
				admin.setA_m_approval(rs.getInt("a_m_approval"));
				admin.setA_m_id(rs.getString("a_m_id"));
				admin.setA_m_pw(rs.getString("a_m_pw"));
				admin.setA_m_name(rs.getString("a_m_name"));
				admin.setA_m_gender(rs.getString("a_m_gender"));
				admin.setA_m_part(rs.getString("a_m_part"));
				admin.setA_m_position(rs.getString("a_m_position"));
				admin.setA_m_mail(rs.getString("a_m_mail"));
				admin.setA_m_phone(rs.getString("a_m_phone"));
				admin.setA_m_reg_date(rs.getString("a_m_reg_date"));
				admin.setA_m_mod_date(rs.getString("a_m_mod_date"));
				
				return admin;
			}
		}, a_m_no);
	}
	
	/**
	 * 관리자 정보를 리턴한다.
	 */
	public AdminMemberVo selectAdmin(String a_m_id, String a_m_name, String a_m_mail) {
		String sql = "SELECT * "
				+ "FROM tbl_admin_member "
				+ "WHERE a_m_id = ? AND a_m_name = ? AND a_m_mail = ?";

		Object[] args = new Object[] {a_m_id,  a_m_name, a_m_mail};
		AdminMemberVo adminMemberVo = null;
		/**
		 * queryForObject -> DataAccessException(RunTime Exception) 발생 가능
		 *   - queryForObject() 결과가 null일 경우(해당되는 row가 없을 경우) DataAccessException 발생함
		 *   - 런타임 예외이기 때문에 컴파일러가 체크 x, 개잘자가 직접 try-catch 작성 해야함
		 */
		try {
			adminMemberVo = jdbcTemplate.queryForObject(sql, args, new RowMapper<AdminMemberVo> () {
				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo admin = new AdminMemberVo();
					
					admin.setA_m_no(rs.getInt("a_m_no"));
					admin.setA_m_approval(rs.getInt("a_m_approval"));
					admin.setA_m_id(rs.getString("a_m_id"));
					admin.setA_m_pw(rs.getString("a_m_pw"));
					admin.setA_m_name(rs.getString("a_m_name"));
					admin.setA_m_gender(rs.getString("a_m_gender"));
					admin.setA_m_part(rs.getString("a_m_part"));
					admin.setA_m_position(rs.getString("a_m_position"));
					admin.setA_m_mail(rs.getString("a_m_mail"));
					admin.setA_m_phone(rs.getString("a_m_phone"));
					admin.setA_m_reg_date(rs.getString("a_m_reg_date"));
					admin.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return admin;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return adminMemberVo; 
	}
	
	/**
	 * 관리자의 password를 update 한다.
	 * @param a_m_id
	 * @param newPassword
	 * @return
	 */
	public int updatePassword(String a_m_id, String newPassword) {
		String sql = "UPDATE tbl_admin_member "
				+ "SET a_m_pw = ?, a_m_mod_date = NOW() "
				+ "WHERE a_m_id = ?";
		
		return jdbcTemplate.update(sql, passwordEncoder.encode(newPassword), a_m_id);
	}
}