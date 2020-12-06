<%@page import="board.model.Notice"%>
<%@page import="board.model.NoticeDAO"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="db.DBManager"%>
<%@ page import="java.sql.*"%>
<%@ include file="/inc/lib.jsp"%>
<%


	/*
	수정 요청을 처리하는 jsp ... 수정후  상세보기 페이지로 전환할 것이므로, 
	디자인이 필요없고 오직 db 로직만 잇으면 됨..
	*/
	request.setCharacterEncoding("utf-8"); //전송 파라미터들에 대한 인코딩처리!!(받기전에 처리하자!!)
	String author		=  request.getParameter("author"); //작성자
	String title			=  request.getParameter("title"); //제목
	String content	=  request.getParameter("content"); //내용
	String notice_id	=  request.getParameter("notice_id"); //notice_id
	
	Notice notice = new Notice();//empty..
	notice.setAuthor(author);
	notice.setTitle(title);
	notice.setContent(content);
	notice.setNotice_id(Integer.parseInt(notice_id));
	
	NoticeDAO noticeDAO = new NoticeDAO();	

	int result = noticeDAO.update(notice); //DML쿼리수행
	
	if(result==0){
		out.print(getMsgBack("수정실패"));
	}else{
		out.print(getMsgURL("수정성공", "/board/detail.jsp?notice_id="+notice_id));
	}
	
%>