<%@ page import="pk.ssi.dedlain.models.Task" %>
<%@ page import="pk.ssi.dedlain.utils.Validation" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% Validation.Errors errors = (Validation.Errors) request.getAttribute("errors"); %>
<% Task task = (Task) request.getAttribute("task"); %>

<p class="<%= errors.get("name") != null ? "field-with-errors" : "" %>">
  <label for="name">Nazwa</label><br /> 
  <input id="name" name="name" type="text" size="30" value="<%= task.getName() %>" 
    <%= (!currentUser.isAdmin() ? "disabled='disabled'" : "") %>/>
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
  <textarea id="description" name="description" rows="10" cols="60" 
    <%= (!currentUser.isAdmin() ? "disabled='disabled'" : "") %>><%= task.getDescription() %></textarea>
  <% if (errors.get("description") != null) { %>
    <span class="errors"> 
      <% for (String error : errors.get("description")) { %>
        <%= error %> 
      <% } %> 
    </span>
  <% } %>
</p>

<p class="<%= errors.get("timeEstimation") != null ? "field-with-errors" : "" %>">
	<label for="time-est">Szacowany czas [h]</label><br /> 
	 <input id="time-est" name="timeEstimation" type="text" size="30" 
	   value="<%= task.getTimeEstimation() != null ? task.getTimeEstimation() : "" %>" />
	 <% if (errors.get("timeEstimation") != null) { %>
	   <span class="errors"> 
	     <% for (String error : errors.get("timeEstimation")) { %>
	       <%= error %> 
	     <% } %> 
	   </span>
	 <% } %>
</p>

<% if (task.getId() != null) { %>
  <input type="hidden" name="id" value="<%= task.getId() %>" />
  
		<% if (task.getIterationId() > 0) { %>
		  <p class="<%= errors.get("done") != null ? "field-with-errors" : "" %>">
		    <label for="done">Zakończone</label> 
		    <input type="checkbox" name="done" id="done" value="1"
		      <%= task.isDone() ? "checked='checked'" : "" %> />
		  </p>
		<% } %>
<% } %>