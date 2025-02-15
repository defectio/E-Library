package com.defectio.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.defectio.library.book.BookVo;
import com.defectio.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	/**
	 * 도서 등록 양식 form 페이지를 리턴한다.
	 * @return
	 */
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		return "admin/book/register_book_form";
	}
	
	/**
	 * 작성한 도서 등록 form에 맞게 도서 정보를 DB애 저장한다.
	 *  
	 * @param book
	 * @param file
	 * @return
	 */
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVo book, @RequestParam("file") MultipartFile file) {
		String savedFileName = uploadFileService.upload(file);
		if (savedFileName != null) {  // 파일 저장 성공
			// 서버에 저장된 파일 이름(uuid적용)을 b_thumbnail에 저장한다.
			book.setB_thumbnail(savedFileName);
			
			int result = bookService.registerBookConfirm(book);
			if (result <= 0) {
				// 기등록된 도서이거나 등록에 실패한 경우
				return "admin/book/register_book_ng";
			}
		} else {
			return "admin/book/register_book_ng";
		}
		
		return "admin/book/register_book_ok";
	}
	
	/**
	 * 도서를 검색한다.
	 * @param b_name
	 * @param mav
	 * @return
	 */
	@GetMapping("/searchBookConfirm")
	public ModelAndView searchBookConfirm(@RequestParam("b_name") String b_name, ModelAndView mav)	 {
		List<BookVo> bookList = bookService.searchBookConfirm(b_name);
		
		mav.addObject("bookList", bookList);
		mav.setViewName("admin/book/search_book");
		
		return mav;
	}
	
	/**
	 * 도서의 상세정보를 조회한다.
	 * @param mav
	 * @return
	 */
	@GetMapping("/bookDetail")
	public ModelAndView bookDetail(@RequestParam("b_no") int b_no, ModelAndView mav) {
		BookVo book = bookService.bookDetail(b_no);
		
		mav.addObject("book", book);
		mav.setViewName("admin/book/book_detail");
		
		return mav;
	}
	
	/**
	 * 도서 정보를 수정페이지를 리턴한다.
	 * @param b_no
	 * @param mav
	 * @return
	 */
	@GetMapping("/modifyBookForm")
	public ModelAndView modifyBookForm(@RequestParam("b_no") int b_no, ModelAndView mav) {
		BookVo book = bookService.bookDetail(b_no);
		mav.addObject("book", book);
		mav.setViewName("admin/book/modify_book_form");
		
		return mav;
	}

	/**
	 * 도서 정보를 수정한다.
	 * @param book
	 * @param file
	 * @param mav
	 * @return
	 */
	@PostMapping("/modifyBookConfirm")
	public ModelAndView modifyBookConfirm(BookVo book, @RequestParam("file") MultipartFile file, ModelAndView mav) {

		if (!file.getOriginalFilename().equals("")) {  // 첨부파일을 수정했다면
			// 파일을 새로 저장함
			String savedFileName = uploadFileService.upload(file);
			if (savedFileName != null) {
				book.setB_thumbnail(savedFileName);
			}
		}
		
		int result = bookService.modifyBookConfrim(book);
		if (result <= 0) {
			mav.setViewName("admin/book/modify_book_ng");
		} else {
			/**
			 * 도서 정보 조회 페이지 redirect
			 * addObject로 뷰페이지에서 사용할 파라미터 전달 
			 */
			mav.addObject("b_no",  book.getB_no());
			mav.setViewName("redirect:/book/admin/bookDetail");
		}
		
		// 도서 정보 조회 페이지 redirect
//		return "redirect:/book/admin/bookDetail?b_no="+book.getB_no();
		return mav;
	}
	
	/**
	 * 도서를 삭제한다.
	 * @param b_no
	 * @return
	 */
	@GetMapping("/deleteBookConfirm")
	public String deleteBook(int b_no) {
		int result = bookService.deleteBookConfirm(b_no);
		
		if (result <= 0) {
			return "admin/book/delete_book_ng";
		}
		
		return "admin/book/delete_book_ok";
	}
	
}