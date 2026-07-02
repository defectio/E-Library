<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	<%-- 로그인 form에서 [login] 버튼 클릭 시 호출됨 --%> 
	function loginForm() {
		let form = document.login_form;
		
		<%-- id/pw null 체크 후 submit 호출 --%>
		if (form.a_m_id.value == '') {
			alert('INPUT ADMIN ID.');
			form.a_m_id.focus();
		} else if (form.a_m_pw.value == '') {
			alert('INPUT ADMIN PW.');
			form.a_m_pw.focus();
		} else {
			form.submit();
		}
		
	}

</script>