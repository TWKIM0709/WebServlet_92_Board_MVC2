package kr.or.bit.ajaxutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Reply;

@WebServlet("/ReplyDel")
public class ReplyDel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyDel() {
        super();
    }

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		String msg = "";
		String url = "";
		String idx_fk = "";
		PrintWriter out = response.getWriter();
		try {
			BoardDao dao = new BoardDao();
			idx_fk=request.getParameter("idx");//댓글의 원본 게시글 번호
			String no = request.getParameter("no");//댓글의 순번(PK)
			String pwd = request.getParameter("delPwd");//댓글의 암호
			System.out.println(idx_fk + "/" + no + "/" + pwd + "/");
			
			if(idx_fk == null || no == null || pwd == null || no.trim().equals(""))	{
				request.setAttribute("idk", idx_fk);
				out.print("dataerror");
			} else {
				int result = dao.replyDelete(no, pwd);
			    if(result > 0){
					List<Reply> replylist = dao.replylist(idx_fk);
					request.setAttribute("list", replylist);
			    	out.print("success");
			    }
			}
		} catch (Exception e) {
			out.print("error");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
