<%@page import="pk.ssi.dedlain.models.Iteration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<div id="main">
  <h1 class="page-title"><%= request.getAttribute("title") %></h1>

  <div class="content-wrapper">
    <form action="<%= application.getContextPath() %>/tasks/update" method="post">
      <%@ include file="/WEB-INF/views/tasks/_form.jsp" %>
      
      <p class="submit-cancel">
        <input type="submit" value="Zapisz" /> 
        lub <a href="<%= application.getContextPath() %>/tasks" title="Anuluj">Anuluj</a>
      </p>
    </form>
  </div>
</div>

<div id="sidebar">
<% if (currentUser.isAdmin()) { %>
  <form action="<%= application.getContextPath() %>/tasks/update" method="post">
    <% List<Iteration> iterations = (List<Iteration>) request.getAttribute("iterations"); %>
    <table class="data" cellpadding="0" cellspacing="0">
      <tr>
        <th style="width: 49%"><label for="iteration">Przypisana iteracja</label></th>
        <th>
          <select style="width: 100%" name="iterationId" id="iteration">
            <option>-- Brak --</option>
            <% for (Iteration it : iterations) { %>
              <option value="<%= it.getId() %>" <%= (it.getId() == task.getIterationId() ? "selected='selected'" : "") %>>
                #<%= it.getId() %> (<%= it.getName() %>)
              </option>
            <% } %>
          </select>
        </th>
      </tr>
    </table>
    <input type="hidden" name="id" value="<%= ((Task) request.getAttribute("task")).getId() %>" />
    <input name="update_iteration" type="submit" value="Zmień" />
  </form>
<% } %>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>