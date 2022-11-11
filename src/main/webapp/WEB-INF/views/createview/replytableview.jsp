<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${requestScope.result ==0 }">
		<!-- 댓글달기 실패일 경우 -->
		<script type="text/javascript">
			alert("댓글 입력 실패");
		</script>
	</c:when>
	<c:otherwise>
		<!-- 댓글달기 성공일 경우 -->
		<script type="text/javascript">
			alert("댓글 입력 성공");
		</script>
		<table width="80%" border="1" id="replytable">
						<tr>
							<th colspan="2">REPLY LIST</th>
						</tr>
						<c:forEach var="reply" items="${requestScope.list }">
							<tr align="left">
								<td width="80%">
									[${reply.writer }] : ${reply.content }
									<br> 작성일:${reply.writedate }
								</td>
								<td width="20%">
								<form action="replydeleteok.do" method="POST" name="replyDel">
									<input type="hidden" name="no" value="${reply.no }"> 
									<input type="hidden" name="idx" value="${requestScope.idx }"> 
									password :<input type="password" name="delPwd" size="4"> 
									<input type="button" value="삭제" onclick="reply_del(this.form)">
								</form>
								</td>
							</tr>
						</c:forEach>
			</table>
	</c:otherwise>
</c:choose>