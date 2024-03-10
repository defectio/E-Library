package com.defectio.library.book.admin.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	/**
	 * 파일을 업로드 한다. 
	 * 파일 업로드 성공시 "파일이름.확장자"를 리턴한다.
	 * @param file
	 * @return
	 */
	public String upload(MultipartFile file) {
		boolean result = false;
		
		// File 저장
		String fileOriName = file.getOriginalFilename();  // 파일이름
		String fileExtension = 
				fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());  // 확장자
		String uploadDir = "C:\\library\\upload\\";  // 서버에 파일이 저장되는 위치 지정

		/**
		 *   - UUID 클래스를 통해서 유일한 식별자를 얻는다.
		 *   - "-"를 제거한다. 
		 *   - 서버에 저장 하는 파일이름으로 사용됨
		 *   - 서버에 저장되는 파일 이름의 중복을 피하기 위해 UUID 클래스 이용 
		 */
		UUID uuid = UUID.randomUUID();  
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		// 서버에 저장되는 파일 객체를 생성
		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);

		// 디렉터리 생성
		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		
		try {
			/**
			 * transferTo() : 서버에 파일을 저장한다.
			 */
			file.transferTo(saveFile);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (result) {
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");
			return uniqueName + fileExtension;
		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");
			return null;
		}
	}
	
}
