
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../webjars/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/fiscalCodeError.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@300&family=Montserrat:wght@300&display=swap"
	rel="stylesheet">
<script src="../../webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="../../webjars/jquery/3.0.0/js/jquery.min.js"></script>
<title>The chosen code already exists</title>
</head>
<body style="background-color: #333333">
	<font color="white"> <br>
	<br>
		<div class="container">
			<h1>
				</br>
				<center>Conflict identified: this fiscal code already
					exists!</center>
			</h1>
			</br>
			</br>
			<table class="table table-hover"
				style="background-color: #333333; color: white">
				<thead bgcolor="#745d5d">
					<tr>
						<th scope="col">Fiscal Code</th>
						<th scope="col">Firstname</th>
						<th scope="col">Lastname</th>
						<th scope="col">Age</th>
						<th bgcolor="#39B3D7" scope="col"><center>Update</center></th>
						<th bgcolor="dc3545" scope="col"><center>Delete</center></th>
					</tr>
				<tbody>
					<tr>
						<td><c:out value="${student.fiscalCode}" /></td>
						<td><c:out value="${student.firstName}" /></td>
						<td><c:out value="${student.lastName}" /></td>
						<td><c:out value="${student.age}" /></td>
						<td><center>
								<spring:url
									value="/${academy.code}/student/update/${student.fiscalCode}"
									var="editURL" />
								<a type="button" class="btn btn-info btn-lg btn3d"
									href="${editURL}" role="button"><svg
										xmlns="http://www.w3.org/2000/svg" width="16" height="16"
										fill="currentColor" class="bi bi-pencil-square"
										viewBox="0 0 16 16"> <path
											d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
										<path fill-rule="evenodd"
											d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z" />
										<span class="glyphicon glyphicon-question"></span></svg></a>
							</center></td>
						<td><center>
								<spring:url
									value="/${academy.code}/student/remove/${student.fiscalCode}"
									var="deleteURL" />
								<a class="btn btn-danger btn-lg btn3d" type="button"
									href="${deleteURL}" role="button"> <svg
										xmlns="http://www.w3.org/2000/svg" width="16" height="16"
										fill="currentColor" class="bi bi-trash-fill"
										viewBox="0 0 16 16"> <path
											d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />
									</svg><span class="glyphicon glyphicon-remove"></span></a>
							</center></td>
					</tr>
				</tbody>
			</table>
			&emsp;&emsp;
			<spring:url value="/home" var="homeURL" />
			<a class="btn3d btn btn-white btn-lg" href="${homeURL}" role="button">
				<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
					fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
				<path fill-rule="evenodd"
						d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z" />
				<path fill-rule="evenodd"
						d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z" />
				</svg>
			</a> &emsp;&emsp;
			<spring:url value="/${academy.code }/students" var="studsURL" />
			<a class="btn3d btn btn-white btn-lg" href="${studsURL}"
				role="button"> <svg xmlns="http://www.w3.org/2000/svg"
					width="24" height="24" fill="currentColor" class="bi bi-arrow-left"
					viewBox="0 0 16 16">
  			<path fill-rule="evenodd"
						d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
</svg></a>
		</div>
</body>
</html>