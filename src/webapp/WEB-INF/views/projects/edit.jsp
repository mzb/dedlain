<%@page import="pk.ssi.dedlain.models.ProjectORM"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
  <h1 class="page-title"><%= request.getAttribute("title") %></h1>

  <div class="content-wrapper">
    <form action="<%= application.getContextPath() %>/projects/update" method="post">
      <%@ include file="/WEB-INF/views/projects/_form.jsp" %>
      <p class="submit-cancel">
        <input type="submit" value="Zapisz" /> lub 
        <a href="<%= application.getContextPath() %>/projects" title="Anuluj">Anuluj</a>
      </p>
    </form>
  </div>
</div>

<div id="sidebar">
  <form action="<%= application.getContextPath() %>/project-assignments/update" method="post">
	  <table class="data" cellpadding="0" cellspacing="0">
	    <tr>
	      <th style="width: 10%"></th>
	      <th>Członkowie zespołu</th>
	    </tr>
	  <% List<User> members = (List<User>) request.getAttribute("members"); %>
	  <% for (User user : (List<User>) request.getAttribute("users")) { %>
	    <tr>
	      <td>
	       <input name="user_id[]" value="<%= user.getId() %>" id="user-<%= user.getId() %>" type="checkbox" 
	           <%= (members.contains(user) ? "checked='checked'" : "") %> />
	      </td>
	      <td>
	       <label for="user-<%= user.getId() %>">
	         <a href="<%= application.getContextPath() %>/users/show?id=<%= user.getId() %>" title="<%= user %>"><%= user %></a>
	       </label>
	      </td>
	    </tr>
	  <% } %>
	  </table>
	  <input type="hidden" name="project_id" value="<%= ((Project) request.getAttribute("project")).getId() %>" />
	  <input type="submit" value="Zapisz" />
  </form>
</div>

<%@ include file="/WEB-INF/views/footer.jsp"%>