<%@ page import="pk.ssi.dedlain.models.Project" %>
<%@ page import="pk.ssi.dedlain.utils.Validation" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% Validation.Errors errors = (Validation.Errors) request.getAttribute("errors"); %>
<% Project project = (Project) request.getAttribute("project"); %>

<p class="<%= errors.get("name") != null ? "field-with-errors" : "" %>">
  <label for="name">Nazwa</label><br /> 
  <input id="name" name="name" type="text" size="30" value="<%= project.getName() %>" />
  <% if (errors.get("name") != null) { %>
    <span class="errors"> 
      <% for (String error : errors.get("name")) { %>
        <%= error %> 
      <% } %> 
    </span>
  <% } %>
</p>

<p class="<%= errors.get("description") != null ? "field-with-errors" : "" %>">
  <label for="description">Opis</label><br /> 
  <textarea id="description" name="description" rows="10" cols="60"><%= project.getDescription() %></textarea>
  <% if (errors.get("description") != null) { %>
    <span class="errors"> 
      <% for (String error : errors.get("description")) { %>
        <%= error %> 
      <% } %> 
    </span>
  <% } %>
</p>

<% if (project.getId() != null) { %>
  <input type="hidden" name="id" value="<%= project.getId() %>" />
<% } %>