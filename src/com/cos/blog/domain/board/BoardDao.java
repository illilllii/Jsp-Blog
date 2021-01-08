package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.SaveReqDto;

public class BoardDao {
	public int save(SaveReqDto dto) { // 회원가입
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board(userId, title, content, createDate) VALUES(?,?,?,now())";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public List<Board> findAll(int page) {
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, userId, title, content, readCount, createDate FROM board ORDER BY id DESC LIMIT ?,4";
		List<Board> boards = new ArrayList<Board>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page*4); // 0->0, 1->4, 2->8
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Board board = Board.builder()
						.id(rs.getInt("id"))
						.userId(rs.getInt("userId"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.createDate(rs.getTimestamp("createDate"))
						.build();

				boards.add(board);
			}
			return boards;


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return null;
	}

	public int count() {
		String sql = "SELECT count(*) FROM board";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1); // index count(*)->1, count(*), id이면 id->2
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}
//	public int findByUsername(String username) {
//	String sql = "SELECT * FROM user WHERE username = ?";
//	Connection conn = DB.getConnection();
//	PreparedStatement pstmt = null;
//	ResultSet rs = null;
//	try {
//		pstmt = conn.prepareStatement(sql);
//		pstmt.setString(1, username);
//		rs = pstmt.executeQuery();
//		
//		if(rs.next()) {
//			return 1;	// 있어
//		}
//	} catch(Exception e) {
//		e.printStackTrace();
//	} finally {
//		DB.close(conn, pstmt, rs);
//	}
//	
//	return -1; 		// 없어
//}

}
