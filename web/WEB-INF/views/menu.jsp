<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="pk.ssi.dedlain.models.User"%>
<% User _currentUser = (User) request.getAttribute("current_user"); %>

<% if (_currentUser.getId() != null) { %>
<div id="menu">
	<% String currentPage = (String) request.getAttribute("current_page"); %>
	<ul>
		<li>
			<div id="project-selector">
				<label for="select-project">Projekt</label> <select
					id="select-project">
					<option>-- Wybierz --</option>
				</select>
			</div>
		<li>
		<li
			class="<%= "current_iteration".equals(currentPage) ? "current" : "" %>">
			<a href="<%= application.getContextPath() %>/iterations/show/current"
			title="Bieżąca iteracja">Bieżąca iteracja</a></li>
		<li class="<%= "iterations".equals(currentPage) ? "current" : "" %>">
			<a href="<%= application.getContextPath() %>/iterations"
			title="Lista iteracji">Lista iteracji</a></li>
		<li class="<%= "tasks".equals(currentPage) ? "current" : "" %>">
			<a href="#" title="Zadania">Zadania</a></li>
		<% if (_currentUser.isAdmin()) { %>
		<li class="<%= "users".equals(currentPage) ? "current" : "" %> right">
			<a href="<%= application.getContextPath() %>/users"
			title="Użytkownicy">Użytkownicy</a></li>
		<li
			class="<%= "projects".equals(currentPage) ? "current" : "" %> right">
			<a href="<%= application.getContextPath() %>/projects"
			title="Projekty">Projekty</a></li>
		<% } %>
	</ul>
</div>
<% } %>
