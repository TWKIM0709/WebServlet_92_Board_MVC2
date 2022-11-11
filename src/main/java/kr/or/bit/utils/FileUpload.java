package kr.or.bit.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUpload {
	public FileUpload(){}
	public static boolean upload(String uploadpath, int size, HttpServletRequest request) {
		System.out.println("file upload start");
		boolean result = true;
		try {
			MultipartRequest multi =  new MultipartRequest(
					request, //클라이언가 서버로 요청하면 자동 생성되는 객체(정보)	
					request.getSession().getServletContext().getRealPath(uploadpath), //실 저장할 경로(배포경로)	
					size, //10M
					"UTF-8",
					new DefaultFileRenamePolicy() // 파일 이름 중복되면 (upload > 1.jpg > 1.jpg업로드 > 1_1.jpg)
				);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
}
