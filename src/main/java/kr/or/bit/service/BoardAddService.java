package kr.or.bit.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;

public class BoardAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		MultipartRequest multi = null;
		String msg = "";
		String url = "";
		Board board = new Board();
		
		try {
			multi =  new MultipartRequest(
					request, //클라이언가 서버로 요청하면 자동 생성되는 객체(정보)	
					request.getSession().getServletContext().getRealPath("upload"), //실 저장할 경로(배포경로)	
					1024 * 1024 * 10, //10M
					"UTF-8",
					new DefaultFileRenamePolicy() // 파일 이름 중복되면 (upload > 1.jpg > 1.jpg업로드 > 1_1.jpg)
				);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "File Upload Error";
			url = "BoardWrite.do";
			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);

			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/board/redirect.jsp");
		}
		
		String subject = multi.getParameter("subject");
		String writer = multi.getParameter("writer");
		String email = multi.getParameter("email");
		String homepage = multi.getParameter("homepage");
		String content = multi.getParameter("content");
		String pwd = multi.getParameter("pwd");
		String filename = multi.getOriginalFileName("filename");
		String filesystemname = multi.getFilesystemName("filename");
		int result = 0;

		
		board.setSubject(subject);
		board.setWriter(writer);
		board.setEmail(email);
		board.setHomepage(homepage);
		board.setContent(content);
		board.setPwd(pwd);
		board.setFilename(filename);
		board.setFilesystemname(filesystemname);
		
		try {
			BoardDao dao = new BoardDao();
			
			result = dao.writeok(board);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		if (result > 0) {
			msg = "insert success";
			url = "BoardList.do";
		} else {
			msg = "insert fail";
			url = "BoardWrite.do";
		}

		request.setAttribute("board_msg", msg);
		request.setAttribute("board_url", url);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/views/board/redirect.jsp");

		return forward;

	}

}
