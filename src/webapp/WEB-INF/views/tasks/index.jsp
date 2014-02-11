<%@page import="pk.ssi.dedlain.models.Task"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
  <h1 class="page-title"><%= request.getAttribute("title") %></h1>

  <div class="content-wrapper">
    <table class="data tasks" cellpadding="0" cellspacing="0">
      <tr>
        <th>#</th>
        <th>Zadanie</th>
        <th style="">Opis</th>
        <th style="">Iteracja</th>
        <th style="">Czas ~[h]</th>
        <th style="">&radic;</th>
        <th style="width: 100px"></th>
      </tr>
      <% for (Task task : (List<Task>) request.getAttribute("tasks")) { %>
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
          <% if (task.getIterationId() == 0) { %>
            --
          <% } else { %>
            <a href="<%= application.getContextPath() %>/iterations/show?id=<%= task.getIterationId() %>">#<%= task.getIterationId() %></a>
          <% } %>
        </td>
        <td style="text-align: center">
          <%= task.getTimeEstimation() %>
        </td>
        <td style="text-align: center">
          <%= task.isDone() ? "&radic;" : "" %>
        </td>
        <td>
        <% if (currentUser.isAdmin()) { %>
          <a href="<%= application.getContextPath() %>/tasks/edit?id=<%= task.getId() %>"
             class="action">Edytuj</a> 
           | <a href="<%= application.getContextPath() %>/tasks/delete?id=<%= task.getId() %>"
                 class="action"
                 data-confirm="Czy na pewno chcesz usunąć to zadanie?\nZadanie zostanie także usunięte z przypisanej iteracji"
                 onclick="return formalLink(this)">Usuń</a>
        </td>
        <% } %>
      </tr>
      <% } %>
    </table>
  </div>
</div>

<div id="sidebar">
<% if (currentUser.isAdmin()) { %>
  <a href="<%= application.getContextPath() %>/tasks/new"
     class="button" title="Dodaj nowe zadaniet">Dodaj nowe zadanie</a>
<% } %>
</div>

<%@ include file="/WEB-INF/views/footer.jsp"%>