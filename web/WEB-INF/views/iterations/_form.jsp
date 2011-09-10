<%@ page import="pk.ssi.dedlain.models.Iteration" %>
<%@ page import="pk.ssi.dedlain.utils.Validation" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% Validation.Errors errors = (Validation.Errors) request.getAttribute("errors"); %>
<% Iteration iteration = (Iteration) request.getAttribute("iteration"); %>

<p class="<%= errors.get("name") != null ? "field-with-errors" : "" %>">
  <label for="name">Nazwa</label><br /> 
  <input id="name" name="name" type="text" size="30" value="<%= iteration.getName() %>" />
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
  <textarea id="description" name="description" rows="10" cols="60"><%= iteration.getDescription() %></textarea>
  <% if (errors.get("description") != null) { %>
    <span class="errors"> 
      <% for (String error : errors.get("description")) { %>
        <%= error %> 
      <% } %> 
    </span>
  <% } %>
</p>

<p class="<%= errors.get("startDate") != null ? "field-with-errors" : "" %>">
  <label for="start-date">Data rozpoczęcia (<code>YYYY-MM-DD</code>)</code></label><br /> 
   <input id="start-date" name="startDate" type="text" size="30" 
      value="<%= iteration.getStartDate() == null ? "" : iteration.getStartDate() %>" />
   <% if (errors.get("startDate") != null) { %>
     <span class="errors"> 
       <% for (String error : errors.get("startDate")) { %>
         <%= error %> 
       <% } %> 
     </span>
   <% } %>
</p>

<p class="<%= errors.get("endDate") != null ? "field-with-errors" : "" %>">
  <label for="end-date">Data zakończenia (<code>YYYY-MM-DD</code>)</label><br /> 
   <input id="end-date" name="endDate" type="text" size="30" 
      value="<%= iteration.getEndDate() == null ? "" : iteration.getEndDate() %>" />
   <% if (errors.get("endDate") != null) { %>
     <span class="errors"> 
       <% for (String error : errors.get("endDate")) { %>
         <%= error %> 
       <% } %> 
     </span>
   <% } %>
</p>

<% if (iteration.getId() != null) { %>
  <input type="hidden" name="id" value="<%= iteration.getId() %>" />
<% } %>