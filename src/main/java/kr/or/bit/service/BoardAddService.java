package kr.or.bit.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;

public class BoardAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String subject = request.getParameter("subject");
		String writer = request.getParameter("writer");
		String email = request.getParameter("email");
		String homepage = request.getParameter("homepage");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		String filename = request.getParameter("filename");
		
		int result = 0;

		Board board = new Board();
		
		board.setSubject(subject);
		board.setWriter(writer);
		board.setEmail(email);
		board.setHomepage(homepage);
		board.setContent(content);
		board.setPwd(pwd);
		board.setFilename(filename);
		
		try {
			BoardDao dao = new BoardDao();
			
			result = dao.writeok(board);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		

		// write.jsp 화면 >> writeok.jsp 처리 >> service >> dao > DB 작업 >
		// return dao > return service > writeok.jsp 결과처리 >> 이동 (공통) >> redirect.jsp

		String msg = "";
		String url = "";
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
