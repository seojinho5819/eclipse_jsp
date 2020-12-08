<%@page import="java.util.List"%>
<%@page import="board.model.QnADAO"%>
<%@page import="board.model.QnA"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%	
//DB연동
	
	int totalRecord=26; //총 레코드 수 
	int pageSize=10; //한 페이지당 보여질 레코드 수
	int totalPage =(int)Math.ceil((float)totalRecord/pageSize);// 총 페이지수
	int blockSize=10; //한 블럭당 보여질 페이지 수
	int currentPage=1; //현재 페이지
	currentPage = Integer.parseInt(request.getParameter("currentPage"));
	int firstPage=currentPage - (currentPage-1)%blockSize; //반복문의 시작 값 
	int lastPage=firstPage + (blockSize-1); //반복문의 끝값
	int num=totalRecord - (currentPage-1)*pageSize;//페이지당 시작 번호
%>
<%="totalRecord "+totalRecord+"<br>"%>
<%="pageSize "+pageSize+"<br>"%>
<%="totalPage "+totalPage+"<br>"%>
<%="blockSize "+blockSize+"<br>"%>
<%="currentPage "+currentPage+"<br>"%>
<%="firstPage "+firstPage+"<br>"%>
<%="lastPage "+lastPage+"<br>"%>
<%="num "+num+"<br>" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}
th, td {
  text-align: left;
  padding: 16px;
}
tr:nth-child(even) {
  background-color: #f2f2f2;
}
img{
	box-sizing:border-box;
}
a{
	text-decoration:none;
}
.pageNum{
	font-size:20pt;
	color:red;
	font-weight:bold;
}
</style>
<script>
</script>
</head>
<body>
<table>
  <tr>
    <th>No</th>
    <th>제목</th>
    <th>작성자</th>
	<th>등록일</th>
	<th>조회수</th>
  </tr>

	<%for(int i=1;i<=pageSize;i++){ %>
    <%if(num==0)break; %>
  <tr>
    <td>
    <%=num-- %>
    </td>
    <td>제목입니다</td>
    <td></td>
	<td></td>
	<td></td>
  </tr>
	<%} %>  
 <tr>
	<td colspan="5" style="text-align:center">
		<%if((firstPage-1)>=0){ %> 
		<a href="/qna/list3.jsp?currentPage=<%=firstPage-1%>">◀</a>
		<%}else{ %>
			<a href="javascript:alert('첫페이지입니다');">◀</a>
			<%} %>
		<%for(int i=firstPage;i<=lastPage;i++){%>
		<%if(i>totalPage)break;//페이지를 출력하는 i가 총 페이지수에 도달하면 반복문 탈출하라 %>
		<a href="/qna/list3.jsp?currentPage=<%=i %>"   <%if(currentPage==i){%>class="pageNum"<%}%> >[<%=i %>]</a>
		<%} %>
		
	<%if((lastPage+1)<totalPage){ %> 
		<a href="/qna/list3.jsp?currentPage=<%=lastPage+1%>">▶</a>
		<%}else{ %>
			<a href="javascript:alert('마지막페이지입니다');">▶</a>
			<%} %>							
	</td>
   </tr>
	
	<tr>	
	<td colspan="5" > 
		<button>글등록</button>
	</td>
  </tr>
  <tr>
	<td colspan="5" style="text-align:center"> 
		<%@ include file="/inc/footer.jsp"%>
	</td>
  </tr>

</table>
</body>
</html>