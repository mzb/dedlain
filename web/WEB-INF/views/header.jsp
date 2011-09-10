<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<link href="<%= application.getContextPath() %>/assets/css/layout.css"
	rel="stylesheet" media="screen" type="text/css" />
<link href="<%= application.getContextPath() %>/assets/css/content.css"
	rel="stylesheet" media="screen" type="text/css" />
<script type="text/javascript"
	src="<%= application.getContextPath() %>/assets/js/application.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<% String title = (String) request.getAttribute("title"); %> <% if (title != null && !title.isEmpty()) { %>
	<%= title + " - " %> <% } %> Dedlain (SSI - Projekt)</title>
</head>

<%@ page import="pk.ssi.dedlain.models.User"%>
<%! User currentUser = new User(); %>
<% currentUser = (User) request.getAttribute("current_user"); %>

<body class="<%= (String) request.getAttribute("controller") %>">
	<div id="header">
		<div id="session-links">
			<% if (currentUser.getId() != null) { %>
			<a
				href="<%= application.getContextPath() %>"
				title="<%= currentUser.getName() %>"><%= currentUser.getName() %></a>
			| <a href="<%= application.getContextPath() %>/sessions/delete"
				onclick="return formalLink(this)" title="Wyloguj">Wyloguj</a>
			<% } else { %>
			<a href="<%= application.getContextPath() %>/sessions/new"
				title="Zaloguj">Zaloguj</a>
			<% } %>
		</div>

		<div class="clear"></div>
		<%@ include file="/WEB-INF/views/menu.jsp"%>
	</div>

	<div id="page">

		<%@ page import="pk.ssi.dedlain.utils.RequestMessage"%>
		<%! RequestMessage message = null; %>
		<% message = (RequestMessage) request.getAttribute("message"); %>
		<% if (message != null) { %>
		<div id="message" class="<%= message.type %>">
			<%= message.body %>
		</div>
		<% } %>