/*
�����ͺ��̽� ���� �� ������ ���õ� �ڵ尡 �ʹ��� �ߺ��ǹǷ�, ������ �������� �и�����
�����ϱ� ����!!!
*/
package db;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="user1104";
	String password="1234";
	
	//���Ӱ�ü ��� 
	public Connection getConnection(){
		Connection con =null;//return������ ���� ������
		try{
			Class.forName(driver);
			System.out.println("����̹��ε强��");

			con = DriverManager.getConnection(url,user,password);

		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("����̹��ε�Ұ�");
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return con;
	}
	
	//�ڿ����� 
	public void release(Connection con){//������ ���� ��������..
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
				
			}
		}
	}
	public void release(Connection con, PreparedStatement pstmt){ //DML��
		if(pstmt!=null){
			try{
				pstmt.close();
			}catch(SQLException e){
				
			}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
				
			}
		}
	}
	public void release(Connection con, PreparedStatement pstmt,ResultSet rs){//select��
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException e){
				
			}
		}
		if(pstmt!=null){
			try{
				pstmt.close();
			}catch(SQLException e){
				
			}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException e){
				
			}
		}
	}
}