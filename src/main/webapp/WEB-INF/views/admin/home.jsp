<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../include/title.jsp" />
<!-- bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- bootstrap 5 -->
<link href="<c:url value='/resources/css/admin/home.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="../include/header.jsp" />
	<jsp:include page="./include/nav.jsp" />
	
	<section>
<!-- 		<div id="section_wrap"> -->
<!-- 			<div class="word"> -->
<!-- 				<h3>ADMIN HOME</h3> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</section>
	
	<jsp:include page="../include/footer.jsp" />
	
</body>
</html>