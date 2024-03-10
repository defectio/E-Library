package com.defectio.library.book.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.defectio.library.book.BookVo;

@Component
public class BookDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * DB에 동일한 isbn이 존재 하는지를 리턴한다.
	 * @param b_isbn
	 * @return
	 */
	public boolean isISBN(String b_isbn) {
		String sql = "SELECT count(*) FROM tbl_book WHERE b_isbn = ?";
		
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(sql, Integer.class, b_isbn);
		} catch (DataAccessException e) {
			count = 0;
		}
		
		return (count > 0) ? true : false;
	}
	
	/**
	 * 책을 DB에 저장하고 변경된 행의 개수를 리턴한다.
	 *   
	 * @param book
	 * @return
	 */
	public int insertBook(BookVo book) {
		String sql = "INSERT INTO tbl_book(b_thumbnail, b_name, b_author, "
				+ "b_publisher, b_publish_year, b_isbn, b_call_number, "
				+ "b_rental_able, b_reg_date, b_mod_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

		int result = -1;
				
		Object[] args = new Object[] {
			book.getB_thumbnail(), book.getB_name(), book.getB_author(), book.getB_publisher(),
			book.getB_publish_year(), book.getB_isbn(), book.getB_call_number(), book.getB_rental_able()
		};
		
		result = jdbcTemplate.update(sql, args);
		return result;
	}
	
	/**
	 * 도서 검색 결과를 List로 리턴한다.
	 * @param book
	 * @return
	 */
	public List<BookVo> selectBooksBySearch(String b_name) {
		String sql = "SELECT * FROM tbl_book WHERE b_name LIKE ? ORDER BY b_no DESC";
		
		List<BookVo> books = null;
		
		try {
			books = jdbcTemplate.query(sql, new RowMapper<BookVo>() {
				@Override
				public BookVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVo bookVo = new BookVo();
					
					bookVo.setB_no(rs.getInt("b_no"));
					bookVo.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVo.setB_name(rs.getString("b_name"));
					bookVo.setB_author(rs.getString("b_author"));
					bookVo.setB_publisher(rs.getString("b_publisher"));
					bookVo.setB_publish_year(rs.getString("b_publish_year"));
					bookVo.setB_isbn(rs.getString("b_isbn"));
					bookVo.setB_call_number(rs.getString("b_call_number"));
					bookVo.setB_rental_able(rs.getInt("b_rental_able"));
					bookVo.setB_reg_date(rs.getString("b_reg_date"));
					bookVo.setB_mod_date(rs.getString("b_mod_date"));
					
					return bookVo;
				}
			}, "%" + b_name + "%");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return books;
	}
	
	/**
	 * 도서의 상세 정보를 조회한다.
	 * @param b_no
	 * @return
	 */
	public BookVo selectBook(int b_no) {
		String sql = "SELECT * FROM tbl_book WHERE b_no = ?";
		
		BookVo book = null;
		try {
			book = jdbcTemplate.queryForObject(sql, new RowMapper<BookVo> () {
				@Override
				public BookVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVo bookVo = new BookVo();
					
					bookVo.setB_no(rs.getInt("b_no"));
					bookVo.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVo.setB_name(rs.getString("b_name"));
					bookVo.setB_author(rs.getString("b_author"));
					bookVo.setB_publisher(rs.getString("b_publisher"));
					bookVo.setB_publish_year(rs.getString("b_publish_year"));
					bookVo.setB_isbn(rs.getString("b_isbn"));
					bookVo.setB_call_number(rs.getString("b_call_number"));
					bookVo.setB_rental_able(rs.getInt("b_rental_able"));
					bookVo.setB_reg_date(rs.getString("b_reg_date"));
					bookVo.setB_mod_date(rs.getString("b_mod_date"));
					
					return bookVo;
				}
			}, b_no);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	/**
	 * 도서 정보를 수정한다.
	 * @param book
	 * @return
	 */
	public int updateBook(BookVo book) {
		List<String> args = new ArrayList<String>();
		
		String sql =  "UPDATE tbl_book SET ";
			   if (book.getB_thumbnail() != null) {
				   sql += "b_thumbnail = ?, ";
				   args.add(book.getB_thumbnail());
			   }
			   
			   sql += "b_name = ?, ";
			   args.add(book.getB_name());
			   
			   sql += "b_author = ?, ";
			   args.add(book.getB_author());
			   
			   sql += "b_publisher = ?, ";
			   args.add(book.getB_publisher());
			   
			   sql += "b_publish_year = ?, ";
			   args.add(book.getB_publish_year());
			   
			   sql += "b_isbn = ?, ";
			   args.add(book.getB_isbn());
			   
			   sql += "b_call_number = ?, ";
			   args.add(book.getB_call_number());
			   
			   sql += "b_rental_able = ?, ";
			   args.add(Integer.toString(book.getB_rental_able()));
			   
			   sql += "b_mod_date = NOW() ";
			   
			   sql += "WHERE b_no = ?";
			   args.add(Integer.toString(book.getB_no()));
			   
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 도서를 삭제한다.
	 * @param b_no
	 * @return
	 */
	public int deleteBook(int b_no) {
		String sql  = "DELETE FROM tbl_book WHERE b_no = ?";
		
		int result = -1;
		try {
			result = jdbcTemplate.update(sql, b_no);
		} catch	(DataAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
