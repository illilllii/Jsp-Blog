<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form action="/blog/user?cmd=join" method="post" onsubmit="return valid()">
		<div class="form-group">
			<div class="d-flex justify-content-end">
				<button type="button" class="btn btn-info" onclick="usernameCheck()">중복체크</button>
			</div>
			<input type="text" name="username" id="username" class="form-control"
				placeholder="Enter Username" required />
		</div>
		<div class="form-group">
			<input type="password" name="password" class="form-control"
				placeholder="Enter Password" required />
		</div>
		<div class="form-group">
			<input type="email" name="email" class="form-control"
				placeholder="Enter email" required />
		</div>
		<div class="form-group">
			<div class="d-flex justify-content-end">
				<button type="button" class="btn btn-info" onclick="goPopup();">주소검색</button>
			</div>
			<input type="text" name="address" id="address" class="form-control"
				placeholder="Enter Address" required readonly />
		</div>
		<button type="submit" class="btn btn-primary">회원가입완료</button>
	</form>
</div>
<script>
	var isChecking = false;

	$("#username").change(function(){
	    isChecking = false;
	});


	function valid() {
		if(isChecking == false) {
			alert("아이디 중복체크를 해주세요.");
		}
		return isChecking;
		
	}

	function usernameCheck() {
		// DB에서 확인해서 정상이면 isChecking = true
		var username = $("#username").val();

		$.ajax({
			type: "POST",
			url: "/blog/user?cmd=usernameCheck",
			data: username,
			contentType: "text/plain; charset=utf-8",
			dataType: "text" // 응답 받을 데이터의 타입을 적으면 자바스크립트 오브젝트로 파싱해줌.
		}).done(function(data) {
			if(data === 'ok') {		// 유저네임이 있다는 것
				isChecking = false;
				alert('유저네임이 중복되었습니다.')
			} else {
				isChecking = true;
				alert('해당 유저네임을 사용할 수 있습니다.')
			}
		});

	}
	
	function goPopup() {
		var pop = window.open("/blog/user?cmd=jusoPopup", "pop",
				"width=570,height=420, scrollbars=yes, resizable=yes");
	}

	function jusoCallBack(roadFullAddr) {
		var addressEl = document.querySelector("#address");
		addressEl.value = roadFullAddr;
	}
</script>
</body>
</html>