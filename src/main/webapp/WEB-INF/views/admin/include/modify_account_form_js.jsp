<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

	// 개정 변경 버튼 클릭 시 validate 체크
	function modifyAccountForm() {
// 		console.log('modifyAccountForm() CALLED!!');
		
		let form = document.modify_account_form;
		
		if (form.a_m_name.value == '') {
			alert('INPUT ADMIN NAME.');
			form.a_m_name.focus();
		} else if (form.a_m_gender.value == '') {
			alert('SELECET ADMIN GENDER.');
			form.a_m_gender.focus();
		} else if (form.a_m_part.value == '') {
			alert('INPUT ADMIN PART.');
			form.a_m_part.focus();
		} else if (form.a_m_position.value == '') {
			alert('INPUT ADMIN POSITON.');
			form.a_m_position.focus();
		} else if (form.a_m_mail.value == '') {
			alert('INPUT ADMIN MAIL.');
			form.a_m_mail.focus();
		} else if (form.a_m_phone.value == '') {
			alert('INPUT ADMIN PHONE.');
			form.a_m_phone.focus();
		} else {
			// validate 통과 시 form의 액션이 호출됨
			form.submit();
		}
	}

</script>