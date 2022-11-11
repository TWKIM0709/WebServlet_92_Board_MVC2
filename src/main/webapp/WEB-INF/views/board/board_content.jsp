<%@page import="kr.or.bit.dto.Reply"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.bit.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>board_content</title>
<link rel="Stylesheet"
	href="${pageContext.request.contextPath}/style/default.css" />
</head>
<body>
	<c:set var="board" value="${requestScope.board}" />
	<c:set var="idx" value="${requestScope.idx }" />
	<c:set var="cpage" value="${requestScope.cp}" />
	<c:set var="pagesize" value="${requestScope.ps}" />
	<c:set var="replyList" value="${requestScope.replyList}" />

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div id="pageContainer">
		<div style="padding-top: 30px; text-align: center">
			<center>
				<b>게시판 글내용</b>
				<table width="80%" border="1">
					<tr>
						<td width="20%" align="center"><b> 글번호 </b></td>
						<td width="30%">${idx}</td>
						<td width="20%" align="center"><b>작성일</b></td>
						<td>${board.writedate}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>글쓴이</b></td>
						<td width="30%">${board.writer}</td>
						<td width="20%" align="center"><b>조회수</b></td>
						<td>${board.readnum}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>홈페이지</b></td>
						<td>${board.homepage}</td>
						<td width="20%" align="center"><b>첨부파일</b></td>
						<td>${board.filename}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>제목</b></td>
						<td colspan="3">${board.subject}</td>
					</tr>
					<tr height="100">
						<td width="20%" align="center"><b>글내용</b></td>
						<td colspan="3">${fn:replace(board.content, newLineChar,"<br>")}</td>
					</tr>
					<tr>
						<td colspan="4" align="center"><a
							href="BoardList.do?cp=${cpage}&ps=${pagesize}">목록가기</a> |<a
							href="BoardEdit.do?idx=${idx}&cp=${cpage}&ps=${pagesize}">편집</a> |<a
							href="BoardDelete.do?idx=${idx}&cp=${cpage}&ps=${pagesize}">삭제</a>
							|<a
							href="BoardRewrite.do?idx=${idx}&cp=${cpage}&ps=${pagesize}&subject=${board.subject}">답글</a>
						</td>
					</tr>
				</table>
				<!--  꼬리글 달기 테이블 -->
				<form name="reply" action="ReplyOk.do" method="POST">
						<!-- hidden 태그  값을 숨겨서 처리  -->
						<input type="hidden" name="idx" value="${idx}"> 
						<input type="hidden" name="userid" value=""><!-- 추후 필요에 따라  -->
						<!-- hidden data -->
							<table width="80%" border="1">
								<tr>
									<th colspan="2">덧글 쓰기</th>
								</tr>
								<tr>
									<td align="left">작성자 :
									 	<input type="text" name="reply_writer"><br /> 
									 	내&nbsp;&nbsp;용 : 
									 	<textarea name="reply_content" rows="2" cols="50"></textarea>
									</td>
									<td align="left">
										비밀번호:
										<input type="password" name="reply_pwd" size="4"> 
										<input type="button" value="등록" onclick="reply_check()">
									</td>
								</tr>
							</table>
				</form>
				<!-- 유효성 체크	 -->
				<script type="text/javascript">
					function reply_check() {
						var frm = document.reply;
						if (frm.reply_writer.value == "" || frm.reply_content.value == ""
							|| frm.reply_pwd.value == "") {
									alert("리플 내용, 작성자, 비밀번호를 모두 입력해야합니다.");
							return false;
						}
					//비동기처리
					$.ajax({
						url:"ReplyOk",
						type:"get",
						data:{
							"reply_writer": frm.reply_writer.value,
							"reply_content":frm.reply_content.value,
							"reply_pwd":frm.reply_pwd.value,
							"idx":frm.idx.value
						},
						dataType:"html",
						success:function(element){
							console.log(element);
							frm.reply_writer.value = "";
							frm.reply_content.value = "";
							frm.reply_pwd.value = "";
							$('#replytablearea').empty();
							$('#replytablearea').append(element);
						},
						error:function(xhr){
							console.log(xhr.status);
							alert(xhr + "오류 발생");
						}
					});
					//frm.submit();
					}
					function reply_del(frm) {
						//alert("del");
						//var frm = document.replyDel;
						//alert(frm);
						if (frm.delPwd.value == "") {
							alert("비밀번호를 입력하세요");
							frm.delPwd.focus();
							return false;
						}
						//비동기처리
						$.ajax({
						url:"ReplyDel",
						type:"get",
						data:{
							"idx":frm.idx.value,
							"no":frm.no.value,
							"delPwd":frm.delPwd.value
						},
						dataType:"text",
						success:function(element){
							console.log(element);
							if(element == "dataerror"){
								alert('데이터 누락됨');
							} else if(element == 'error'){
								alert('덧글 삭제 실패');
							} else if(element == 'success'){
								alert('덧글 삭제 성공');
								$(frm).parent().parent().remove();
							}
						},
						error:function(xhr){
							console.log(xhr.status);
							alert(xhr + "오류 발생");
						}
					});
						//frm.submit();
					}
				</script>
				<br>
				<!-- 꼬리글 목록 테이블 -->
				<c:if test="${not empty replyList}">
					<c:forEach var="reply" items="${replyList}">
						<div id="replytablearea">
						<table width="80%" border="1">
							<tr>
								<th colspan="2">REPLY LIST</th>
							</tr>
							<tr align="left">
								<td width="80%">
								[${reply.writer}] : ${reply.content}
								<br> 작성일:${reply.writedate}
								</td>
								<td width="20%">
								<form action="ReplyDeleteOk.do" method="POST" name="replyDel">
									<input type="hidden" name="no" value="${reply.no}"> 
									<input type="hidden" name="idx" value="${idx}"> 
									password :<input type="password" name="delPwd" size="4"> 
									<input type="button" value="삭제" onclick="reply_del(this.form)">
								</form>
								</td>
							</tr>
						</table>
						</div>
					</c:forEach>
				</c:if>
			</center>
		</div>
	</div>
</body>
</html>





