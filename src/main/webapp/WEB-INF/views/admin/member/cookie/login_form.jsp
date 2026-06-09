<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../../include/title.jsp" />
<link href="<c:url value='/resources/css/admin/login_form.css' />" rel="stylesheet" type="text/css">
<jsp:include page="../../include/login_js.jsp" />
</head>
<body>
	<jsp:include page="../../../include/header.jsp" />
	<jsp:include page="../../include/nav.jsp" />
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>Member LOGIN FORM(cookie)</h3>
			</div>
			<div class="login_form">
				<form action="<c:url value='/member/loginConfirm' />" name="login_form" method="post">
					<input type="text" name="m_id"> <br>
					<input type="password" name="m_pw"> <br>
					<input type="button" value="login"> 
					<input type="reset" value="reset">
				</form>
			</div>
		</div>
	</section>
	
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>