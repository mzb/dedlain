<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
	<h1 class="page-title"><%= request.getAttribute("title") %></h1>

	<div class="content-wrapper">
		<form action="<%= application.getContextPath() %>/users/create"
			method="post">
			<%@ include file="/WEB-INF/views/users/_form.jsp"%>
			<p class="submit-cancel">
				<input type="submit" value="Zapisz" /> lub <a
					href="<%= application.getContextPath() %>/users" title="Anuluj">Anuluj</a>
			</p>
		</form>
	</div>
</div>

<div id="sidebar"></div>

<%@ include file="/WEB-INF/views/footer.jsp"%>