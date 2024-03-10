<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/resources/css/include/header.css' />" rel="stylesheet" type="text/css">

<!-- bootstrap 5 -->
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script> -->
<!-- bootstrap 5 -->

<script src="https://code.jquery.com/jquery-3.6.3.js" integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM=" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function() {
		let pathname = $(location).attr('pathname');
		let isIncludeUser = pathname.includes('/user');
		
		if (isIncludeUser) {
			$('#header_wrap .menu ul li a.user').css('text-decoration', 'green wavy underline');
		} else {
			$('#header_wrap .menu ul li a.admin').css('text-decoration', 'red wavy underline');
		}
	});
</script>
<header>
	
	<div id="header_wrap">
		
		<div class="menu">
			<ul>
				<li><a class="user" href="<c:url value='/' />">USER HOME</a></li>
				<li><a class="admin" href="<c:url value='/admin' />">ADMIN HOME</a></li>
			</ul>
		</div>
		
		<div class="title">
			<h3>한국 도서관 - 도서 대여 서비스</h3>
		</div>
	</div>
	
<!-- 	<div id="header_wrap"> -->
<!-- 		<div class="menu"> -->
<!-- 			<nav class="navbar navbar-expand-sm bg-primary navbar-dark"> -->
<!-- 				<div class="container-fluid"> -->
<!--     				<a class="navbar-brand" href="#">한국 도서관 - 도서 대여 서비스</a> -->
<!--   				</div> -->
				
<!-- <!-- 				nav 우측 정렬 -->
<!-- 				<div class="container-fluid justify-content-end"> -->
<!-- 					<ul class="navbar-nav"> -->
<!-- 						<li class="nav-item"><a class="nav-link" href="#">USER HOME</a></li> -->
<!-- 						<li class="nav-item"><a class="nav-link" href="#">ADMIN HOME</a></li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 			</nav> -->
<!-- 		</div> -->
<!-- 	</div> -->
</header>