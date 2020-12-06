<%@page import="board.model.ImageBoardDAO"%>
<%@page import="board.model.ImageBoard"%>
<%@page import="common.FileManager"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/lib.jsp"%>
<% 
	String saveDir="C:/javaee_workspace/BoardApp/WebContent/data";
	int maxSize=3*1024*1024; //3m byte
	ImageBoardDAO dao =new ImageBoardDAO();
	
%>
<%
	//실습했던 예제보다 기능이 더 추가됨 , db에 넣어야 함 ...DAO이용
	
	//업로드 컴포넌트에 대한 설정을 하기위해 FileItemFactory객체를 이용해야한다
	DiskFileItemFactory itemFactory = new DiskFileItemFactory();
	itemFactory.setRepository(new File(saveDir));	
	itemFactory.setSizeThreshold(maxSize);

	ServletFileUpload upload = new ServletFileUpload(itemFactory);
	
	request.setCharacterEncoding("utf-8");
	List<FileItem> items=upload.parseRequest(request); //업로드된 정보 파싱!!! 각각의 컴포넌트들을 FileItem단위로 쪼갠다...
	
	ImageBoard board = new ImageBoard();
	
	for(FileItem item:items){
		if(item.isFormField()){//textfield라면..
			//vo에 텍스트 필드의 값을 담자!!
			if(item.getFieldName().equals("author")){
				board.setAuthor(item.getString());
			}else if(item.getFieldName().equals("title")){
				board.setTitle(item.getString());
			}else if(item.getFieldName().equals("content")){
				board.setContent(item.getString());
			}
			
		}else{//textfield가 아니라면 ..
			String newName = System.currentTimeMillis()+FileManager.getExtend(item.getName());
			String destFile = 	saveDir+"/"+newName;
			File file = new File(destFile);
			item.write(file);
			
			out.print("업로드완료");
			board.setFilename(newName);//vo에 파일명 값을 담자!!
			
		}
	}

	int result = dao.insert(board);
	
	if(result ==0){
		out.print(getMsgBack("등록실패"));
	}else{
		out.print(getMsgURL("등록성공", "/imageboard/list.jsp"));
	}
%>
