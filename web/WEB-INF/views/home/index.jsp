<%@ page import="pk.ssi.dedlain.models.User"%>
<%@ page import="pk.ssi.dedlain.utils.Validation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>

<div id="main">
  <h1 class="page-title"><%= request.getAttribute("title") %></h1>
  
  <div class="content-wrapper">
		<form action="<%= application.getContextPath() %>/update" method="post">
			<% Validation.Errors errors = (Validation.Errors) request.getAttribute("errors"); %>
			
			<p class="<%= errors.get("name") != null ? "field-with-errors" : "" %>">
	      <label for="name">Nazwa użytkownika</label><br /> <input id="name"
	        name="name" type="text" size="30" value="<%= currentUser.getName() %>" />
	    </p>
			
			<p class="<%= errors.get("password") != null ? "field-with-errors" : "" %>">
			  <label for="password">Hasło</label><br /> <input type="password"
			    name="password" id="password" size="30"
			    value="<%= currentUser.getPassword() %>" />
			  <% if (errors.get("password") != null) { %>
			  <span class="errors"> <% for (String error : errors.get("password")) { %>
			    <%= error %> <% } %> </span>
			  <% } %>
			</p>
			
			<p class="<%= errors.get("email") != null ? "field-with-errors" : "" %>">
			  <label for="email">Adres e-mail</label><br /> <input id="email"
			    name="email" type="text" size="30" value="<%= currentUser.getEmail() %>" />
			  <% if (errors.get("email") != null) { %>
			  <span class="errors"> <% for (String error : errors.get("email")) { %>
			    <%= error %> <% } %> </span>
			  <% } %>
			</p>
			
	    <p class="submit-cancel">
        <input type="submit" value="Zmień" />
      </p>
	  </form>
  </div>
</div>

<div id="sidebar"></div>