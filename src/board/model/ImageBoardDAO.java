package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;

public class ImageBoardDAO {
	DBManager dbManager = new DBManager();//ImagesBoardDAO인스턴스가 생성될때
																	//DBManager의 인스턴스도 같이 생성됨
	//create(insert)
	public int insert(ImageBoard board) {
		Connection con =null;
		PreparedStatement pstmt = null;
		int result = 0;
		con=dbManager.getConnection();
		String sql = "insert into imageboard(board_id,author,title,content,filename) values(seq_imageboard.nextval,?,?,?,?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, board.getAuthor());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFilename());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con,pstmt);
		}
		
		return result;
	}
	//selectAll()
	public ArrayList selectAll() {
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ImageBoard> list = new ArrayList<ImageBoard>();
		
		String sql = "select * from imageboard order by board_id desc";
		con = dbManager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ImageBoard imageBoard = new ImageBoard();
				imageBoard.setBoard_id(rs.getInt("board_id"));
				imageBoard.setAuthor(rs.getString("author"));
				imageBoard.setTitle(rs.getString("title"));
				imageBoard.setContent(rs.getString("content"));
				imageBoard.setRegdate(rs.getString("regdate"));
				imageBoard.setFilename(rs.getString("filename"));
				imageBoard.setHit(rs.getInt("hit"));
				
				list.add(imageBoard);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbManager.release(con,pstmt,rs);
		}
		
		return list;
		
		
	}
	//select
	public ImageBoard select(int board_id) {
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		ImageBoard board = null;
		
	
		String sql = "select * from imageboard where board_id=?";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,board_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new ImageBoard();
				board.setBoard_id(rs.getInt("board_id"));
				board.setAuthor(rs.getString("author"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
				board.setFilename(rs.getString("filename"));
				
				
			}
			pstmt=con.prepareStatement("update imageboard set hit=hit+1 where board_id=?");
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt,rs);
		}
		return board;
		
	}
	
	//update
	public int update(ImageBoard board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result =0;
		String sql = "update imageboard set author=?,title=?,content=?,filename=?,where board_id=?";
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getAuthor());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFilename());
			pstmt.setInt(5, board.getBoard_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
		
	}
	
	//delete
	public int delete(int board_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "delete from imageboard where board_id=?";
		con = dbManager.getConnection();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		
		return result;
	}
}
