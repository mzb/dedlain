<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
	<h1 class="page-title">Logowanie</h1>

	<div class="content-wrapper">
		<form action="<%= application.getContextPath() %>/sessions/create"
			method="post">
			<p>
				<label for="login">Login</label><br /> <input id="login"
					name="login" type="text" size="30" />
			</p>
			<p>
				<label for="password">Hasło</label><br /> <input id="password"
					name="password" type="password" size="30" />
			</p>
			<p>
				<input type="submit" value="Zaloguj" />
			</p>
		</form>
	</div>
</div>

<div id="sidebar"></div>

<%@ include file="/WEB-INF/views/footer.jsp"%>