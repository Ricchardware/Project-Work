<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body {
  background-image: url("/custom-pages/Academy background.jpg");
  background-repeat: no-repeat;
  background-position: center 35%;
  background-size: cover;

}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Academy List</title>
<link href="../../webjars/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" />
<script src="../../webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="../../webjars/jquery/3.0.0/js/jquery.min.js"></script>
</head>
<body style="background-color: #333333">
	<font color="white"> <br>
		<h1>
			<center style="font-family: Georgia">ACADEMIES</center>
		</h1> </br>
		<div class="container">
			<table class="table table-hover"
				style="background-color: #333333; color: white;">
				<thead bgcolor="#745d5d">
					<tr>
						<th scope="col">Code</th>
						<th scope="col">Name</th>
						<th scope="col">Location</th>
						<th scope="col">Start Date</th>
						<th scope="col">Start Date</th>
						<th bgcolor="#17a2b8" scope="col">Update</th>
						<th bgcolor="28a745" scope="col">View Students</th>
						<th bgcolor="dc3545" scope="col">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${academies}" var="academy">
						<tr>
							<td>${academy.code}</td>
							<td>${academy.title}</td>
							<td>${academy.location}</td>
							<td>${academy.startDate}</td>
							<td>${academy.endDate}</td>
							<td><center>
									<spring:url value="/${academy.code}/update" var="editURL" />
									<a class="btn btn-info" href="${editURL}" role="button"><svg
											xmlns="http://www.w3.org/2000/svg" width="24" height="24"
											fill="currentColor" class="bi bi-pencil-square"
											viewBox="0 0 16 16"> <path
											d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
										<path fill-rule="evenodd"
											d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
										</svg></a>
								</center></td>
							<td><center>
									<spring:url value="/${academy.code}/students" var="studsURL" />
									<a class="btn btn-success" href="${studsURL}" role="button"><svg
											xmlns="http://www.w3.org/2000/svg" width="24" height="24"
											fill="currentColor" class="bi bi-person-lines-fill"
											viewBox="0 0 16 16"> <path
											d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z" />
										</svg></a>
								</center></td>
							<td><center>
									<spring:url value="/${academy.code}/remove" var="deleteURL" />
									<a class="btn btn-danger" href="${deleteURL}" role="button">
										<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
											fill="currentColor" class="bi bi-trash-fill"
											viewBox="0 0 16 16"> <path
											d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />
										</svg>
									</a>
								</center></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<spring:url value="/academies/add" var="addURL" />

			<a class="btn btn-success" href="${addURL}" role="button">Add Academy&nbsp; <svg xmlns="http://www.w3.org/2000/svg" width="16"
					height="16" fill="currentColor" class="bi bi-mortarboard-fill"
					viewBox="0 0 16 16"> <path
					d="M8.211 2.047a.5.5 0 0 0-.422 0l-7.5 3.5a.5.5 0 0 0 .025.917l7.5 3a.5.5 0 0 0 .372 0L14 7.14V13a1 1 0 0 0-1 1v2h3v-2a1 1 0 0 0-1-1V6.739l.686-.275a.5.5 0 0 0 .025-.917l-7.5-3.5Z" />
				<path
					d="M4.176 9.032a.5.5 0 0 0-.656.327l-.5 1.7a.5.5 0 0 0 .294.605l4.5 1.8a.5.5 0 0 0 .372 0l4.5-1.8a.5.5 0 0 0 .294-.605l-.5-1.7a.5.5 0 0 0-.656-.327L8 10.466 4.176 9.032Z" />
				</svg>
			</a>
		</div>
</body>
</html>