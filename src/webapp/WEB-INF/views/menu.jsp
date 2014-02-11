<%@page import="pk.ssi.dedlain.models.Project"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="pk.ssi.dedlain.models.User"%>
<% User _currentUser = (User) request.getAttribute("current_user"); %>
<% Project currentProject = (Project) request.getAttribute("current_project"); %>

<% if (_currentUser.getId() != null) { %>
<div id="menu">
	<% String currentPage = (String) request.getAttribute("current_page"); %>
	<ul>
		<li>
			<form id="project-selector" action="<%= application.getContextPath() %>/projects" method="post">
			 <% List<Project> userProjects = (List<Project>) request.getAttribute("user_projects"); %>
				<label for="select-project">Projekt</label> 
				<select id="select-project" name="current_project" 
				  onchange="document.getElementById('project-selector').submit()">
					<% for (Project p : userProjects) { %>
					 <option value="<%= p.getId() %>" 
					   <% if (currentProject.equals(p)) { %> selected="selected" <% } %>>
					   <%= p.getName() %>
					 </option>
					<% } %>
				</select>
			</form>
		<li>
	  <% if (session.getAttribute("current_project") != null) { %>
			<li
				class="<%= "current_iteration".equals(currentPage) ? "current" : "" %>">
				<a href="<%= application.getContextPath() %>/iterations/show/current"
				title="Bieżąca iteracja">Bieżąca iteracja</a></li>
			<li class="<%= "iterations".equals(currentPage) ? "current" : "" %>">
				<a href="<%= application.getContextPath() %>/iterations"
				title="Lista iteracji">Iteracje</a></li>
			<li class="<%= "tasks".equals(currentPage) ? "current" : "" %>">
				<a href="<%= application.getContextPath() %>/tasks" title="Zadania">Zadania</a></li>
		<% } %>		
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
