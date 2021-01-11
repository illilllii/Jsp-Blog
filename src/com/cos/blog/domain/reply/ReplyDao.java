package com.cos.blog.domain.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.reply.dto.ReplyRespDto;
import com.cos.blog.domain.reply.dto.SaveReqDto;

public class ReplyDao {
	public int save(SaveReqDto dto) {

		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int generateKey;
		String sql = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?,?,?,now())";

		try {

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setInt(2, dto.getBoardId());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				generateKey = rs.getInt(1);
				System.out.println("생성된 키(ID) : " + generateKey);

				if (result == 1) {
					return generateKey;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;

	}

	public ReplyRespDto findById(int id) {

		// SELECT r.id, r.boardId, r.content, r.userId, u.username FROM reply r inner
		// join user u on r.userId = u.id where r.id=1
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT r.id, r.boardId, r.content, r.userId, u.username ");
		sb.append("FROM reply r inner join user u ");
		sb.append("on r.userId = u.id where r.id= ?");
		String sql = sb.toString();

		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// select b.id, b.title, b.content, b.readCount, u.username from board b inner
			// join user u on b.userid = u.id where b.id = 1;
			pstmt.setInt(1, id); // 0->0, 1->4, 2->8
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ReplyRespDto replyRespDto = new ReplyRespDto();
				// Reply reply = new Reply();
				replyRespDto.setId(rs.getInt("id"));
				replyRespDto.setUserId(rs.getInt("userId"));
				replyRespDto.setBoardId(rs.getInt("boardId"));
				replyRespDto.setUsername(rs.getString("username"));
				replyRespDto.setContent(rs.getString("content"));

				return replyRespDto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return null;
	}

	public List<ReplyRespDto> findAll(int boardId) {

		// String sql = "SELECT * FROM reply WHERE boardId = ? ORDER BY id DESC";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT r.id, r.boardId, r.content, r.userId, u.username ");
		sb.append("FROM reply r inner join user u ");
		sb.append("on r.userId = u.id where r.boardId= ? ORDER BY r.id DESC");
		String sql = sb.toString();

		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReplyRespDto> replys = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			// select b.id, b.title, b.content, b.readCount, u.username from board b inner
			// join user u on b.userid = u.id where b.id = 1;
			pstmt.setInt(1, boardId); // 0->0, 1->4, 2->8
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReplyRespDto replyRespDto = new ReplyRespDto();
				// Reply reply = new Reply();
				replyRespDto.setId(rs.getInt("id"));
				replyRespDto.setUserId(rs.getInt("userId"));
				replyRespDto.setBoardId(rs.getInt("boardId"));
				replyRespDto.setUsername(rs.getString("username"));
				replyRespDto.setContent(rs.getString("content"));

				replys.add(replyRespDto);

			}

			return replys;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return null;
	}

	public int deleteById(int id) {
		String sql = "DELETE FROM reply WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch(Exception e) {
			
		} finally {
			DB.close(conn, pstmt);
		}
		
		return -1;
	}
}
