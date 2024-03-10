<%@page import="com.defectio.library.admin.member.AdminMemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/resources/css/admin/include/nav.css' />" rel="stylesheet" type="text/css">
<jsp:include page="./nav_js.jsp" />
<!-- <nav> -->
	<div id="nav_wrap">
<%-- 		<% --%>
<!-- // 			AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo"); -->
<!-- // 			if (loginedAdminMemberVo != null) { -->
<%-- 		%> --%>
<!-- 		<div class="menu"> -->
<!-- 			<ul> -->
<%-- 				<li><a href="<c:url value='/admin/member/logoutConfirm' />">로그아웃</a></li> --%>
<%-- 				<li><a href="<c:url value='/admin/member/modifyAccountForm' />">계정수정</a></li> --%>
				
<%-- 				<c:if test="${loginedAdminMemberVo.a_m_id eq 'super_admin'}"> --%>
<%-- 					<li><a href="<c:url value='/admin/member/listupAdmin' />">관리자목록</a></li> --%>
<%-- 				</c:if> --%>
				
<%-- 				<li><a href="<c:url value='/book/admin/getRentalBooks' />">대출도서</a></li> --%>
<%-- 				<li><a href="<c:url value='/book/admin/getAllBooks' />">전체도서</a></li> --%>
<%-- 				<li><a href="<c:url value='/book/admin/getHopeBooks' />">희망도서(입고처리)</a></li> --%>
<%-- 				<li><a href="<c:url value='/book/admin/registerBookForm' />">도서등록</a></li> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
<%-- 		<% --%>
<!-- // 		} else { -->
<%-- 		%> --%>
<!-- 		<div class="menu"> -->
<!-- 			<ul> -->
<%-- 				<li><a href="<c:url value='/admin/member/loginForm' />">로그인</a></li> --%>
<%-- 				<li><a href="<c:url value='/admin/member/createAccountForm' />">회원가입</a></li> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
<%-- 		<% --%>
<!-- // 		} -->
<%-- 		%> --%>

		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<%
			AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
			if (loginedAdminMemberVo != null) {
		%>
			<div class="container-fluid">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="<c:url value='/admin/member/logoutConfirm' />">로그아웃</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:url value='/admin/member/modifyAccountForm' />">계정수정</a></li>
				
					<c:if test="${loginedAdminMemberVo.a_m_id eq 'super_admin'}">
						<li class="nav-item"><a href="<c:url value='/admin/member/listupAdmin' />">관리자목록</a></li>
					</c:if>
				
					<li class="nav-item"><a class="nav-link" href="<c:url value='/book/admin/getRentalBooks' />">대출도서</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:url value='/book/admin/getAllBooks' />">전체도서</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:url value='/book/admin/getHopeBooks' />">희망도서(입고처리)</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:url value='/book/admin/registerBookForm' />">도서등록</a></li>
				</ul>
			</div>
		<%
		} else {
		%>
			<div class="container-fluid justify-content-end">
				<ul class="navbar-nav">
<%-- 					<li><a href="<c:url value='/admin/member/loginForm' />">로그인</a></li> --%>
<%-- 					<li><a href="<c:url value='/admin/member/createAccountForm' />">회원가입</a></li> --%>
					
					<li class="nav-item">
						<a class="nav-link" href="<c:url value='/admin/member/loginForm' />">로그인</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="<c:url value='/admin/member/createAccountForm' />">회원가입</a>
					</li>
				</ul>
			</div>
		<%
		}
		%>
		</nav>

			<!-- 검색바 -->
			<div class="search">
				<form class="d-flex"
					action="<c:url value='/book/admin/searchBookConfirm' />"
					name="search_book_form" method="get">
					<!-- 				<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search"> -->
					<input class="form-control me-2" type="text" placeholder="Search"
						aria-label="Search">
					<!-- 				<button class="btn btn-outline-success" type="submit">Search</button> -->
					<input class="btn btn-outline-success" type="button" value="search"
						onclick="searchBookForm();">
				</form>
			</div>
			<!-- 		<div class="search"> -->
			<%-- 				<form action="<c:url value='/book/admin/searchBookConfirm' />" name="search_book_form" method="get"> --%>
			<!-- 					<input type="text" name="b_name" placeholder="Enter the name of the book you are looking for."> -->
			<!-- 					<input type="button" value="search" onclick="searchBookForm();"> -->
			<!-- 				</form> -->
			<!-- 		</div> -->
	</div>
	
</nav>