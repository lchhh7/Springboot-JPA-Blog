<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="layout/header.jsp"%>
<div class="container">
	<c:forEach var="boardlist" items="${boards.content}">
		<!-- boardController에 있는 model.addAttri~~("boards")가 넘어옴 -->
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${boardlist.title}</h4>
				<a href="/board/${boardlist.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>

	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link"
					href="?page=${boards.number-1}">이전</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number-1}">이전</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${boards.number}>=2">

				<li class="page-item"><a class="page-link"
					href="?page=${boards.number-1}">${boards.number-1}</a></li>
				<li class="page-item disabled"><a class="page-link"
					href="?page=${boards.number}">${boards.number}</a></li>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number+1}">${boards.number+1}</a></li>
			</c:when>

			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number}">${boards.number}</a></li>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number+1}">${boards.number+1}</a></li>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number+2}">${boards.number+2}</a></li>
			</c:otherwise>
		</c:choose>


		<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link"
					href="?page=${boards.number+1}">다음</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number+1}">다음</a></li>
			</c:otherwise>
		</c:choose>

	</ul>
</div>
<br />
<%@include file="layout/footer.jsp"%>