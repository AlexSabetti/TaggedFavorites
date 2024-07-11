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
		<nav class="navbar navbar-dark fixed-top bg-dark">
			<div class="container-fluid">
				<h2 class="text-bg-dark">Welcome, <c:out value="${user.userName}"/></h2>
				<button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="offcanvas offcanvas-end text-bg-dark" tabindex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
					<div class="offcanvas-header">
						<h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">Test Test Test</h5>
						<button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
					</div>
					<div class="offcanvas-body">
						
					</div>
					<div class="offcanvas-footer">
						<ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
							<li class="nav-item">
								<a class="nav-link active text-center" aria-current="page" href="/logout">Logout</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</nav>
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
		<form:form modelAttribute="newMedia" action="/taggedfavorites/game/create">
			<form:label path="name">Name:</form:label>
			<form:input path="name"/>
			<br>
			<form:label path="category">Category:</form:label>
			<form:input path="category"/>
			<br>
			<form:label path="status">Status:</form:label>
			<form:input path="status"/>
			
			<input type="text" id="tagIput" placeholder="Enter Tags">
			<button type="button" onclick="addTag()">Add Tag</button>
			<ul id="tagList"></ul>
			
			<input type="hidden" id="hiddenTags" name="tags"/>
			
			<input type="submit" value="Submit">
		
		</form:form>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>

</script>
</body>
</html>