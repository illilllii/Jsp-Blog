package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.reply.dto.ReplyRespDto;
import com.cos.blog.domain.reply.dto.SaveReqDto;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReplyController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		ReplyService replyService = new ReplyService();

		HttpSession session = request.getSession();

		if (cmd.equals("save")) {
			BufferedReader br = request.getReader();
			String reqData = br.readLine();
			Gson gson = new Gson();
			SaveReqDto dto = gson.fromJson(reqData, SaveReqDto.class);
			System.out.println("dto: " + dto);
			
			ReplyRespDto replyRespDto = null;
			CommonRespDto<ReplyRespDto> commonRespDto = new CommonRespDto<>();
			int result = replyService.댓글쓰기(dto);
			if(result != -1) {
				replyRespDto = replyService.댓글찾기(result);
				commonRespDto.setStatusCode(1);
				commonRespDto.setData(replyRespDto);
			} else {
				commonRespDto.setStatusCode(-1);
			}
		
			String responseData = gson.toJson(commonRespDto);
			System.out.println("responseData"+responseData);
			Script.responseData(response, responseData);
			
		}
	}

}
