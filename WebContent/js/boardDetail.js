

function addReply(data) {
	var replyItem = `<li id="reply-${data.id}" class="media">`;
	replyItem += `<div class="media-body">`;
	replyItem += `<strong class="text-primary">${data.username}</strong>`;
	replyItem += `<p>${data.content}</p></div>`;
	replyItem += `<div class="m-2">`;
	replyItem += `<i onclick="deleteReply(${data.id})" class="material-icons">delete</i></div></li>`;
	$("#reply__list").prepend(replyItem);
}

function replySave(userId, boardId) {
	//console.log($("#content").text());
	//console.log($("#content").val());

	var data = {
		userId: userId,
		boardId: boardId,
		content: $("#content").val()
	}
	$.ajax({
		type: "post",
		url: "/blog/reply?cmd=save",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"

	}).done(function(result) {
		if (result.statusCode == 1) {
			console.log(result);
			addReply(result.data);
		} else {
			alert("댓글쓰기 실패");
		}
	});
}

function deleteById(boardId) {
	// ajax로 delete 요청 (Method: POST)
	// 요청과 응답을 json
	$.ajax({
		type: "POST",
		url: "/blog/board?cmd=delete&id=" + boardId,
		dataType: "json"
	}).done(function(result) {
		if (result.statusCode == 1) {
			location.href = "index.jsp";
		} else {
			alert("삭제에 실패하였습니다.");
		}
	});
}