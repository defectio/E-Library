package com.defectio.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defectio.library.book.BookVo;

@Service
public class BookService {

	final static public int BOOK_ISNB_ALREADY_EXIST = 0;  // 이미 등록된 도서
	final static public int BOOK_REGISTER_SUCCESS = 1;  // 신규 도서 등록 성공
	final static public int BOOK_REGISTER_FAIL = -1;  // 신규 도서 등록 실패

	@Autowired
	BookDao bookDao;
	
	/**
	 * ISBN이 이미 등록된 도서가 아닐경우 신규 도서를 등록한다.
	 * @param book
	 * @return
	 */
	public int registerBookConfirm(BookVo book) {
	
		boolean isISBN = bookDao.isISBN(book.getB_isbn());
		
		// true : 기등록된 도서, false : 미등록된 도서
		if (!isISBN) {  // 미등록된 도서
			int result = bookDao.insertBook(book);
			
			if (result > 0) {
				return BOOK_REGISTER_SUCCESS;
			} else {
				return BOOK_REGISTER_FAIL;
			}
		} else {  // 기등록된 도서
			return BOOK_ISNB_ALREADY_EXIST;
		}
	}
	
	/**
	 * 도서 검색 결과를 리턴한다.
	 * @param b_name
	 * @return
	 */
	public List<BookVo> searchBookConfirm(String b_name) {
		return bookDao.selectBooksBySearch(b_name);
	}
	
	/**
	 * 도서의 상세정보를 리턴한다. 
	 * @param b_no
	 * @return
	 */
	public BookVo bookDetail(int b_no) {
		return bookDao.selectBook(b_no);
	}
	
	/**
	 * 도서 정보를 수정한다.
	 * @param book
	 * @return
	 */
	public int modifyBookConfrim(BookVo book) {
		return bookDao.updateBook(book);
	}
	
	public int deleteBookConfirm(int b_no) {
		return bookDao.deleteBook(b_no);
	}
}