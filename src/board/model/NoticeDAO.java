package board.model;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * DAO란 
 * Data Access Object를 의미하는 어플리케이션의 설계 분야용어
 * Data Access란 데이터 베이스와의 CreateReadUpdateDelete(CRUD)작업을 전담한다는 의미
 * 
 */
import java.util.ArrayList;

import db.DBManager;

public class NoticeDAO {
	// 재사용 고려안한 swing만의 로직 작성
	// insert는 글 한건 ~~ 하나의 VO
	DBManager dbManager = new DBManager();

	public int regist(Notice notice) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		con = dbManager.getConnection();
		String sql = "insert into notice(author,title,content) values(?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getAuthor());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt);
		}
		return result;

	}
	//모든레코드 가져오기
	public ArrayList selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		ArrayList<Notice> list = new ArrayList<Notice>();//rs를 대체할 녀석들
		
		con = dbManager.getConnection();
		String sql ="select * from notice order by notice_id desc";
		try {
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			//rs는 레코드가 복수개 이므로 ,즉 여러개이므로 VO또한 여러개가 필요하고 이 VO를 한꺼번에모아서
			//반환해야 하므로,집합형 자료형이 필요하다!! 객체를 모아놓은 집합을 지원하는 프레임웍은
			//CollectionFramework이므로 이중 하나의 api를 이용해본다
			while(rs.next()) {
				Notice notice = new Notice();//텅빈 empty상태의 vo생성 
				//notice에 rs의 정보를 모두 ~~ 옴겨심자!!
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				
				//notice 변수가 반복문을빠져나가 사라지기전 list에 담자
				list.add(notice);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
	
		return list;
	}
	
	
	
	
	//게시물 1건 가져오기
	public Notice select(int notice_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		Notice notice = null;//rs대신 데이터 1건을 담을 객체
		
		
		String sql = "select * from notice where notice_id=?";
		
		con=dbManager.getConnection();//접속객체 열기
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			
			rs=pstmt.executeQuery();
			
			
			//지금탄생한 rs는 곧 죽는다 따라서 rs를 대체할 객체가 필요하다
			//rs는 레코드 한건을 담는 객체이므로, 레코드 1건을 담아 전달용으로 사용되는 VO를 이용하자
			if(rs.next()) {//레코드가 존재할때 VO를 올리자
				notice = new Notice();//텅빈 empty상태의 vo생성 
				//notice에 rs의 정보를 모두 ~~ 옴겨심자!!
				notice.setNotice_id(rs.getInt("notice_id"));
				notice.setAuthor(rs.getString("author"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setRegdate(rs.getString("regdate"));
				notice.setHit(rs.getInt("hit"));
				
			}
			//조회수 증가
			sql="update notice set hit=hit+1 where notice_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,notice_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			dbManager.release(con,pstmt,rs);
		}
		
		return notice;
		
	}
	//게시물 1건 수정
	public int update(Notice notice) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="update notice set author=? , title=?, content=? where notice_id=?";
		int result=0;
		
		con=dbManager.getConnection();
		try {
			pstmt=con.prepareStatement(sql);//준비 
			pstmt.setString(1, notice.getAuthor());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());
			pstmt.setInt(4, notice.getNotice_id());
			result=pstmt.executeUpdate();//쿼리수행			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	public int delete(int notice_id) {
		PreparedStatement pstmt=null;
		Connection con = null;
		int result =0;
		String sql = "delete from notice where notice_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con,pstmt);
		}
		return result;
	}
	
	
	

}
