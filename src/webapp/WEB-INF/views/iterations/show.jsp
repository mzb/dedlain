<%@page import="pk.ssi.dedlain.controllers.TasksController"%>
<%@page import="pk.ssi.dedlain.models.Iteration"%>
<%@page import="pk.ssi.dedlain.models.Task"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
  <% Iteration iteration = (Iteration) request.getAttribute("iteration"); %>
	<h1 class="page-title">
	   <%= request.getAttribute("title") %> - <%= iteration.getName() %></h1>

	<div class="content-wrapper">
	  <p><%= iteration.getDescription() %></p>
	
    <table class="data tasks" cellpadding="0" cellspacing="0">
      <tr>
        <th>#</th>
        <th>Zadanie</th>
        <th style="">Opis</th>
        <th style="">Czas ~[h]</th>
        <th style="">&radic;</th>
      </tr>
      <% List<Task> tasks = (List<Task>) request.getAttribute("tasks"); %>
      <% for (Task task : tasks) { %>
      <tr class="<%= task.isDone() ? "done" : "" %>">
        <td><%= task.getId() %></td>
        <td>
          <a href="<%= application.getContextPath() %>/tasks/edit?id=<%= task.getId() %>"><%= task.getName() %></a>
        </td>
        <td title="<%= task.getDescription() %>">
          <%= task.getDescription().length() > 100 ? 
              task.getDescription().substring(0, 100) + "..." : task.getDescription() %>
        </td>
        <td style="text-align: center">
          <%= task.getTimeEstimation() %>
        </td>
        <td style="text-align: center">
          <%= task.isDone() ? "&radic;" : "" %>
        </td>
      </tr>
      <% } %>
    </table>
	</div>
</div>

<div id="sidebar">
  <table class="data" cellpadding="0" cellspacing="0">
    <tr>
      <th>Data rozpoczęcia</th>
      <td><%= iteration.getStartDate() %></td>
    </tr>
    <tr>
      <th>Data zakończenia</th>
      <td><%= iteration.getEndDate() %></td>
    </tr>
    <tr>
      <th>&sum; szacowany czas [h]</th>
      <td><%= request.getAttribute("totalTimeEst") %></td>
    </tr>
    <tr>
      <th>Progres</th>
      <td>
        <%= request.getAttribute("numDoneTasks") %>/<%= tasks.size() %> 
        (<%= request.getAttribute("progress") %>%)
      </td>
    </tr>
  </table>
<% if (currentUser.isAdmin()) { %>
  <a href="<%= application.getContextPath() %>/iterations/edit?id=<%= iteration.getId() %>"
             class="button">Edytuj</a>
  <a href="<%= application.getContextPath() %>/tasks"
             class="button">Dodaj zadanie</a>
<% } %> 
</div>

<%@ include file="/WEB-INF/views/footer.jsp"%>