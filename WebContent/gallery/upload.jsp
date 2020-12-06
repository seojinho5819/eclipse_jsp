<%@page import="java.io.File"%>
<%@page import="common.FileManager"%>
<%@page import="java.io.IOException"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	/*
클라이언트가 전송한 제목 텍스트 및 바이너리 파일을 서버의 특정 디렉토리에 저장해보자
이를 업로드라 한다!!!

*/
request.setCharacterEncoding("utf-8");
//String msg = request.getParameter("msg");

String saveDirectory = "C:/javaee_workspace/BoardApp/WebContent/data";
int maxSize = 2 * 1024 * 1024; //2Mbyte
String encording = "utf-8";

try {
	MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxSize, encording);//업로드
	String msg = multi.getParameter("msg");
	out.print("님이 전송한 메세지 :" + msg);
	//업로드가 완료된 후, 즉 서버의 저장소에 파일이 존재하게 된 후 해야할 일!!
	//파일명을 개발자가 정한 규칙으로 한다.. 현재 시간의 밀리 세컨드까지 구해보자
	long time = System.currentTimeMillis();
	//구한시간에 확장자를 붙이면 최종적으로 ~~~~.jpg
	out.print(time);
	
	//방금 업로드한 파일명 알아맞추기(업로드 컴포넌트가 알고있다 ..api조사)
	String ori = multi.getOriginalFileName("photo");
	out.print("당신이 업로드한 로컬 원래 파일명은"+ori);
	
	String ext = FileManager.getExtend(ori);
	String filename = time +"."+ext;
	out.print("내가 조작한 파일명은"+filename);
	
	File saveFile = multi.getFile("photo");
	saveFile.renameTo(new File(saveDirectory+"/"+filename));//파일명 교체
	
	//클라이언트에게 전송할 응답정보를 가진 객체
	//클라이언트 브라우져로 하여금 지정한 URL로 재접속을 시도하게 만듦
	//response.sendRedirect("/gallery/photo_list.jsp");
	out.print("업로드완료");
} catch (IOException e) {
	e.printStackTrace();//로그에 에로 출력
	out.print("업로드 용량이 너무 큽니다");
}
%>