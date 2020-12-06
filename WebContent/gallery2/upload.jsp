<%@page import="common.FileManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.File"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.DefaultFileItemFactory"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
   /*
   파일 업로드 컴포넌트의 종류엔 여러가지가 있지만, 그중 아파치의 공식 업로드 컴포넌트를 사용해본다.
   */
   String saveDir = "C:/JavaEE_workspace/BoardApp/WebContent/data";
   int maxSize = 2*1024*1024;
   //업로드 객체를 생성해주는 팩토리 객체 : 주로 설정을 담당함(서버의 저장경로, 파일의 용량제한..)
   DefaultFileItemFactory itemFactory = new DefaultFileItemFactory();
   //itemFactory.setRepository(new File("저장경로"));
   itemFactory.setRepository(new File(saveDir)); //저장될 위치
   itemFactory.setSizeThreshold(maxSize); //용량제한
   
   //이 객체가 실제 업로드를 수행함.
   ServletFileUpload upload = new ServletFileUpload(itemFactory);  //설정정보를 생성자의 인수로 전달.
   
   //FileItem은 클라이언트의 전송 정보 하나하나를 의미한다.
   //즉, html에서의 input 텍스트박스, file컴포넌트들
   //우리의 경우 input type = "text"가 FileItem에 담기고
   //우리의 경우 input type = "file"가 FileItem에 담기고
   
   request.setCharacterEncoding("utf-8");
   List<FileItem> items = upload.parseRequest(request); //업로드 컴포넌트에게 클라이언트의 요청 정보를 전달한다.
      for(FileItem item : items){
         //out.print("저는 "+ item.getFieldName());
         out.print(item.getFieldName()+"필드의 값은 : "+ item.getString()+"<br>");
         //반복문으로 처리되다보니, 파일만 따로 처리를 하려면 로직이 필요함.
         if(!item.isFormField()){ //텍스트박스가 아닌것 isFormField의 이름에 속지말자.
            //원래파일이름
            String ori = item.getName();
            //확장자구하기
            String ext = FileManager.getExtend(ori);
            //업로드 처리하자! 메모리상의 이미지 정보를 실제 물리적 파일로 저장하자!
            String filename = System.currentTimeMillis()+"."+ext;
            File file = new File(saveDir+"/"+filename);
            item.write(file); //저장정보를 파일클래스의 인스턴스로 전달해야한다.
            
            out.print("보고서 작성<br>");
            
            
            out.print("원래 파일명 : "+item.getName()+"<br>");
            out.print("생성된 파일명 : "+filename+"<br>");
            out.print("저장 경로 : "+saveDir+"<br>");
            out.print("업로드된 파일크기 : "+item.getSize()+"bytes <br>");
            
         }
      }
%>