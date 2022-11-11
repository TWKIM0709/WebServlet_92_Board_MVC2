package kr.or.bit.service;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;

public class BoardEditOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String idx = "";
		MultipartRequest multi = null;
		String msg = "";
		String url = "";
		ActionForward forward = null;
		
		try {
			BoardDao dao = new BoardDao();
			
//			if (idx == null || idx.trim().equals("")) {
//				msg = "글번호 입력 오류";
//				url = "BoardList.do";
//			}else {
				try {
					multi =  new MultipartRequest(
							request, //클라이언가 서버로 요청하면 자동 생성되는 객체(정보)	
							request.getSession().getServletContext().getRealPath("upload"), //실 저장할 경로(배포경로)	
							1024 * 1024 * 10, //10M
							"UTF-8",
							new DefaultFileRenamePolicy() // 파일 이름 중복되면 (upload > 1.jpg > 1.jpg업로드 > 1_1.jpg)
						);
					System.out.println(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Board board = new Board();
				board.setIdx(Integer.parseInt(multi.getParameter("idx")));
				board.setPwd(multi.getParameter("pwd"));
				board.setWriter(multi.getParameter("writer"));
				board.setEmail(multi.getParameter("email"));
				board.setHomepage(multi.getParameter("homepage"));
				board.setSubject(multi.getParameter("subject"));
				board.setContent(multi.getParameter("content"));
				board.setFilename(multi.getOriginalFileName("filename"));
				board.setFilesystemname(multi.getFilesystemName("filename"));
				
				if (idx == null || idx.trim().equals("")) {
					msg = "글번호 입력 오류";
					url = "BoardList.do";
				}
				
				int result = dao.boardEdit(board);
				
				if (result > 0) {
					msg = "edit success";
					url = "BoardList.do";
				} else {
					msg = "edit fail";
					url = "BoardEdit.do?idx=" + idx;
				}
				
			//}
			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);
			
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/board/redirect.jsp");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return forward;
	}

}
