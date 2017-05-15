package com.hyunbin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hyunbin.dto.BoardVO;

public class BoardDAO {
	
	private BoardDAO(){
	}
	
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance(){
		return instance;
	}
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/board"; 
	
	void connect() {
		try {
			Class.forName(jdbc_driver);

			conn = DriverManager.getConnection(jdbc_url,"root","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<BoardVO> selectAllBoards(){
		String sql = "select * from board order by num desc";
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		ResultSet rs = null;
		
		try{
			connect();
			pstmt= conn.prepareStatement(sql);
			rs= pstmt.executeQuery();
			while(rs.next()){
				BoardVO bVo= new BoardVO();
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setPass(rs.getString("pass"));
				bVo.setEmail(rs.getString("email"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				bVo.setReadcount(rs.getInt("readcount"));
				list.add(bVo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
		return list;
	}
	
	public void insertBoard(BoardVO bVo){
		
		String sql="insert into board(name, email, pass, title, content) values(?,?,?,?,?)";
		
		
		try{
			connect();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bVo.getName());
			pstmt.setString(2, bVo.getEmail());
			pstmt.setString(3, bVo.getPass());
			pstmt.setString(4, bVo.getTitle());
			pstmt.setString(5, bVo.getContent());
		
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
	}
	
	public void updateReadCount(String num){
		String sql="update board set readcount=readcount+1 where num=?";
		
		
		
		try{
			connect();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
	}
	
	public BoardVO selectOneBoardByNum(String num){
		String sql = "select * from board where num = ?";
		
		BoardVO bVo= null;
		ResultSet rs = null;
		
		try{
			connect();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			rs=pstmt.executeQuery();
			if(rs.next()){
				
				bVo =  new BoardVO();
				
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setPass(rs.getString("pass"));
				bVo.setEmail(rs.getString("email"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				bVo.setReadcount(rs.getInt("readcount"));
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			disconnect();
		}
		return bVo;
	}
	
	public void updateBoard(BoardVO bVo){
		
		String sql="update board set name=?,email=?,pass=?,title=?,content=? where num=?";
		
		try{
			connect();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bVo.getName());
			pstmt.setString(2, bVo.getEmail());
			pstmt.setString(3, bVo.getPass());
			pstmt.setString(4, bVo.getTitle());
			pstmt.setString(5, bVo.getContent());
			pstmt.setInt(6, bVo.getNum());
			
			pstmt.executeUpdate();
		}catch(SQLException e ){
			
			e.printStackTrace();
		}finally{
			disconnect();
		}
	}
	
	public BoardVO checkPassWord(String pass, String num){
		String sql= "select * from board where pass=? and num=?";
		
		ResultSet rs= null;
		BoardVO bVo = null;
		
		try{
			connect();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, pass);
			pstmt.setString(2, num);
			
			rs=pstmt.executeQuery();
			
				if(rs.next()){
				
				bVo =  new BoardVO();
				
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setPass(rs.getString("pass"));
				bVo.setEmail(rs.getString("email"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				bVo.setReadcount(rs.getInt("readcount"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
		return bVo;
	}
	
	public void deleteBoard(String num){
		
		String sql="delete from board where num=?";
		
		
		try{
			connect();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, num);
			
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			
			disconnect();
		}
	}
}
