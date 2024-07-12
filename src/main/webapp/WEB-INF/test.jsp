<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri= "http://www.springframework.org/tags/form" %>
<%@ page isErrorPage ="true" %>

<!DOCTYPE html>
<html>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<!-- YOUR own local CSS -->
<link rel="stylesheet" href="/css/style.css"/>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		
	</div>
	<br><br>
	<div class="container text-center">
		<c:forEach var="media" items="${medias}">
			<div>
				<c:out value="Name: ${media.name}"/>
				<br>
				<c:out value="Status: ${media.status}"/>
				<br>
				<c:out value="Category: ${media.category}"/>
			</div>
			<br>
			<div>
				<c:forEach var="tag" items="${media.tags}">
					<div>
						<c:out value="${tag.name}"/>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
		
		<h1>Create a game</h1>
		<form:form modelAttribute="newMedia" action="/test/create">
			<form:label path="name">Name:</form:label>
			<form:input path="name"/>
			<br>
			<form:label path="category">Category:</form:label>
			<form:input path="category"/>
			<br>
			<form:label path="status">Status:</form:label>
			<form:input path="status"/>
			<div class="row">
				<div class="col">
					<h4>Available Tags</h4>
					<ul id='taglist' class="text-center ft-5" style="list-style:none"></ul>
					
				</div>
				<br>
				<div class="col">
					<h4>Applied Tags</h4>
					<ul id='untaglist' class="ft-5" style="list-style:none"></ul>
				</div>
			</div>
			
			
			
			<input type="hidden" id="hiddenList" name="tags"/>
			<input type="submit" value="Submit">
		
		</form:form>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="/TagListUpdate.js"></script>
</body>
</html>