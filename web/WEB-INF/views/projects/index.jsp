<%@page import="java.util.List"%>
<%@page import="pk.ssi.dedlain.models.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
	<h1 class="page-title"><%= request.getAttribute("title") %></h1>

	<div class="content-wrapper">
		<table class="data" cellpadding="0" cellspacing="0">
			<tr>
				<th>Projekt</th>
				<th style="width: 350px">Opis</th>
				<th style="width: 60px">Wielkość zespołu</th>
				<th style="width: 100px"></th>
			</tr>
			<% for (Project project : (List<Project>) request.getAttribute("projects")) { %>
				<tr>
					<td>
					 <a href="<%= application.getContextPath() %>/iterations?project_id=<%= project.getId() %>">
							<%= project.getName() %></a>
					</td>
					<td><%= project.getDescription() %></td>
					<td style="text-align: center">4</td>
					<td>
					  <a href="<%= application.getContextPath() %>/projects/edit/?id=<%= project.getId() %>"
						   class="action">Edytuj</a>
					  <a href="<%= application.getContextPath() %>/projects/delete/?id=<%= project.getId() %>"
						   class="action"
						   data-confirm="Czy na pewno chcesz usunąć projekt <%= project %>?"
						   onclick="return formalLink(this)">Usuń</a>
			    </td>
				</tr>
			<% } %>
		</table>
	</div>
</div>

<div id="sidebar">
	<a href="<%= application.getContextPath() %>/projects/new"
		class="button" title="Dodaj nowy projekt">Dodaj nowy projekt</a>
</div>

<%@ include file="/WEB-INF/views/footer.jsp"%>