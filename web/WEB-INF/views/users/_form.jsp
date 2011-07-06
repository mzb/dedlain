<%@ page import="pk.ssi.dedlain.models.User"%>
<%@ page import="pk.ssi.dedlain.utils.Validation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<% Validation.Errors errors = (Validation.Errors) request.getAttribute("errors"); %>
<% User user = (User) request.getAttribute("user"); %>

<p class="<%= errors.get("login") != null ? "field-with-errors" : "" %>">
	<label for="login">Login</label><br /> <input id="login" name="login"
		type="text" size="30" value="<%= user.getLogin() %>" />
	<% if (errors.get("login") != null) { %>
	<span class="errors"> <% for (String error : errors.get("login")) { %>
		<%= error %> <% } %> </span>
	<% } %>
</p>

<p
	class="<%= errors.get("password") != null ? "field-with-errors" : "" %>">
	<label for="password">Hasło</label><br /> <input type="password"
		name="password" id="password" size="30"
		value="<%= user.getPassword() %>" />
	<% if (errors.get("password") != null) { %>
	<span class="errors"> <% for (String error : errors.get("password")) { %>
		<%= error %> <% } %> </span>
	<% } %>
</p>

<p class="<%= errors.get("name") != null ? "field-with-errors" : "" %>">
	<label for="name">Nazwa użytkownika</label><br /> <input id="name"
		name="name" type="text" size="30" value="<%= user.getName() %>" />
</p>

<p class="<%= errors.get("email") != null ? "field-with-errors" : "" %>">
	<label for="email">Adres e-mail</label><br /> <input id="email"
		name="email" type="text" size="30" value="<%= user.getEmail() %>" />
	<% if (errors.get("email") != null) { %>
	<span class="errors"> <% for (String error : errors.get("email")) { %>
		<%= error %> <% } %> </span>
	<% } %>
</p>

<p class="<%= errors.get("admin") != null ? "field-with-errors" : "" %>">
	<label for="admin">Administrator</label> <input type="checkbox"
		name="admin" id="admin" value="1"
		<%= user.isAdmin() ? "checked='checked'" : "" %> />
</p>

<% if (user.getId() != null) { %>
<input type="hidden" name="id" value="<%= user.getId() %>" />
<% } %>