package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
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
	
	
	public DetailRespDto findById(int id) {
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select b.id, b.title, b.content, b.readCount, u.username ");
		sb.append("from board b inner join user u ");
		sb.append("on b.userid = u.id ");
		sb.append("where b.id = ?");
		String sql = sb.toString();
		try {
			pstmt = conn.prepareStatement(sql);
			//select b.id, b.title, b.content, b.readCount, u.username from board b inner join user u on b.userid = u.id where b.id = 1;
			pstmt.setInt(1, id); // 0->0, 1->4, 2->8
			rs = pstmt.executeQuery();
			if (rs.next()) {
				DetailRespDto dto = new DetailRespDto();
				dto.setId(rs.getInt("b.id"));
				dto.setTitle(rs.getString("b.title"));
				dto.setContent(rs.getString("b.content"));
				dto.setReadCount(rs.getInt("b.readCount"));
				dto.setUsername(rs.getString("u.username"));
				
				return dto;
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return null;
	}
	public int updateByReadCount(int id) {
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET readCount = readCount + 1 WHERE id = ?";
		
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		
		return -1;
	}


}
