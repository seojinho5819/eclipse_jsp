package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;

public class CommentsDAO {
	DBManager dbmanager = new DBManager();
	
	
	public int insert(Comments comments) {
		Connection con = null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=dbmanager.getConnection();
		String sql="insert into comments(news_id, author, msg) values(?,?,?)";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt( 1, comments.getNews_id());
			pstmt.setString(2, comments.getAuthor());
			pstmt.setString(3, comments.getMsg());
			result = pstmt.executeUpdate(); //½ÇÇà
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbmanager.release(con, pstmt);
		}
		return result;
	}
	
	//뉴스 한건에 소속된 하위 코멘트 목록 가져오기
	public List selectAll(int news_id) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		String sql="select * from comments where news_id=?";
		
		con=dbmanager.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Comments comments = new Comments();
				comments.setComments_id(rs.getInt("comments_id"));
				comments.setNews_id(rs.getInt("news_id")); //fk
				comments.setAuthor(rs.getString("author"));
				comments.setMsg(rs.getString("msg"));
				comments.setCdate(rs.getString("cdate"));
				list.add(comments);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbmanager.release(con, pstmt, rs);
		}
		return list;
	}
	public int delete(int comments_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int result =0;
		String sql = "delete from comments where comments_id=?";
		con = dbmanager.getConnection();
		try {
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, comments_id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbmanager.release(con, pstmt);
		}
		
				
				
				
				return result;
	}
}