package com.cos.blog.domain.reply.dto;

import lombok.Data;

@Data
public class ReplyRespDto {
	private int id;
	private int boardId;
	private int userId;
	private String username;
	private String content;
}
