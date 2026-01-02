<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
<%-- 로그인 정보를 입력받아 controller에 전달한다. --%>
function loginForm() {
	let form = document.login_form;
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