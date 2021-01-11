<%@page import="com.cos.blog.domain.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<!-- 해당 페이지로 직접 URL(자원에 직접 파일.확장자) 접근을 하게 되면 또 파일 내부에서 세션 체크를 해야함. -->
<!-- 필터에 .jsp로 접근하는 모든 접근을 막아버리면 됨. -->

<div class="container">

	<!-- POST, GET, DELETE, PUT => POST, GET -->


	<c:if test="${dto.userId == sessionScope.principal.id}">
		<a href="/blog/board?cmd=updateForm&id=${dto.id}"
			class="btn btn-warning">수정</a>
		<button class="btn btn-danger" onclick="deleteById(${dto.id})">삭제</button>
	</c:if>

	<br /> <br />
	<h6 class="m-2">
		작성자 : <i>${dto.username}</i> 조회수 : <i>${dto.readCount}</i>
	</h6>
	<br />
	<h3 class="m-2">
		<b>${dto.title}</b>
	</h3>
	<hr />
	<div class="form-group">
		<div class="m-2">${dto.content}</div>
	</div>

	<hr />

	<!-- 댓글 박스 -->
	<div class="row bootstrap snippets">
		<div class="col-md-12">
			<div class="comment-wrapper">
				<div class="panel panel-info">
					<div class="panel-heading m-2">
						<b>Comment</b>
					</div>
					<div class="panel-body">
						<form action="/blog/reply?cmd=save" method="post">
							<input type="hidden" name="userId" value="${sessionScope.principal.id}" />
							<input type="hidden" name="boardId" value="${dto.id}" />
							<textarea id="reply__write__form" name="content" class="form-control"
								placeholder="write a comment..." rows="2"></textarea>
							<br>
							<button onclick="#" class="btn btn-primary pull-right">댓글쓰기</button>
						</form>

						
						<div class="clearfix"></div>
						<hr />

						<!-- 댓글 리스트 시작-->
						<ul id="reply__list" class="media-list">

							<!-- 댓글 아이템 -->
							<li id="reply-1" class="media">
								<div class="media-body">
									<strong class="text-primary">홍길동</strong>
									<p>댓글입니다.</p>
								</div>
								<div class="m-2">

									<i onclick="#" class="material-icons">delete</i>

								</div>
							</li>

						</ul>
						<!-- 댓글 리스트 끝-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 댓글 박스 끝 -->


</div>

<script>
	function deleteById(boardId) {
		// ajax로 delete 요청 (Method: POST)
		// 요청과 응답을 json
		$.ajax({
			type:"POST",
			url:"/blog/board?cmd=delete&id="+boardId,
			dataType: "json"
		}).done(function(result) {
			if(result.statusCode == 1) {
				location.href="index.jsp";
			} else {
				alert("삭제에 실패하였습니다.");
			}
		});
	}
</script>
</body>
</html>





