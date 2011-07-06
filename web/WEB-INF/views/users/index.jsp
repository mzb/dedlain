<%@page import="java.util.List"%>
<%@page import="pk.ssi.dedlain.models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
	<h1 class="page-title"><%= request.getAttribute("title") %></h1>

	<div class="content-wrapper">
		<table class="data" cellpadding="0" cellspacing="0">
			<tr>
				<th>Login</th>
				<th style="">Nazwa użytkownika</th>
				<th style="">Adres e-mail</th>
				<th style="">Admin?</th>
				<th style="width: 100px"></th>
			</tr>
			<% for (User user : (List<User>) request.getAttribute("users")) { %>
			<tr>
				<td><a
					href="<%= application.getContextPath() %>/users/show?id=<%= user.getId() %>">
						<%= user.getLogin() %></a></td>
				<td><%= user.getName() %></td>
				<td><%= user.getEmail() %></td>
				<td style="text-align: center"><input name="admin"
					type="checkbox" value="1" disabled="disabled"
					<%= user.isAdmin() ? "checked=\"checked\"" : "" %> /></td>
				<td><a
					href="<%= application.getContextPath() %>/users/edit?id=<%= user.getId() %>"
					class="action">Edytuj</a> <% if (! user.getId().equals(currentUser.getId())) { %>
					| <a
					href="<%= application.getContextPath() %>/users/delete?id=<%= user.getId() %>"
					class="action"
					data-confirm="Czy na pewno chcesz usunąć konto użytkownika <%= user %>?"
					onclick="return formalLink(this)">Usuń</a> <% } %>
				</td>
			</tr>
			<% } %>
		</table>
	</div>
</div>

<div id="sidebar">
	<a href="<%= application.getContextPath() %>/users/new" class="button"
		title="Dodaj nowego użytkownika">Dodaj nowego użytkownika</a>
</div>

<%@ include file="/WEB-INF/views/footer.jsp"%>