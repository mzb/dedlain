<%@page import="pk.ssi.dedlain.models.Iteration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
	<h1 class="page-title"><%= request.getAttribute("title") %></h1>

	<div class="content-wrapper">
    <table class="data tasks" cellpadding="0" cellspacing="0">
      <tr>
        <th>#</th>
        <th>Nazwa</th>
        <th style="">Opis</th>
        <th style="">Data rozpoczęcia</th>
        <th style="">Data zakończenia</th>
        <th style="width: 100px"></th>
      </tr>
      <% for (Iteration it : (List<Iteration>) request.getAttribute("iterations")) { %>
      <tr>
        <td><%= it.getId() %></td>
        <td>
          <a href="<%= application.getContextPath() %>/iterations/show?id=<%= it.getId() %>"><%= it.getName() %></a>
        </td>
        <td title="<%= it.getDescription() %>">
          <%= it.getDescription().length() > 100 ? 
              it.getDescription().substring(0, 100) + "..." : it.getDescription() %>
        </td>
        <td>
          <%= it.getStartDate() %>
        </td>
        <td>
          <%= it.getEndDate() %>
        </td>
        <td>
        <% if (currentUser.isAdmin()) { %>
          <a href="<%= application.getContextPath() %>/iterations/edit?id=<%= it.getId() %>"
             class="action">Edytuj</a> 
           | <a href="<%= application.getContextPath() %>/iterations/delete?id=<%= it.getId() %>"
                 class="action"
                 data-confirm="Czy na pewno chcesz usunąć iterację <%= it %>?"
                 onclick="return formalLink(this)">Usuń</a>
        <% } %>
        </td>
      </tr>
      <% } %>
    </table>	  
	</div>
</div>

<div id="sidebar">
<% if (currentUser.isAdmin()) { %>
  <a href="<%= application.getContextPath() %>/iterations/new"
     class="button" title="Dodaj nowe zadaniet">Dodaj nową iterację</a>
<% } %>
</div>

<%@ include file="/WEB-INF/views/footer.jsp"%>