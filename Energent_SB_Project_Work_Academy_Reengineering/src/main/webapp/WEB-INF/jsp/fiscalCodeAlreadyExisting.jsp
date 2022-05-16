
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
<script src="../../webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="../../webjars/jquery/3.0.0/js/jquery.min.js"></script>
<title>The chosen code already exists</title>
</head>
<body style="background-color:#333333">
 
	<font color ="white">
	<h1>
		</br><center>Conflict identified: this fiscal code already exists!</center>
	</h1>
	</br></br>
	<div class="container">
	<table class="table" style="background-color:#333333; color:white">
		<tbody>
			<tr>
				<td><c:out value= "${student.firstName}" /></td>
				<td><c:out value= "${student.lastName}" /></td>
				<td><c:out value= "${student.fiscalCode}" /></td>
				<td><c:out value= "${student.age}" /></td>
				<td><spring:url value="/${academy.code}/student/update/${student.fiscalCode}"
								var="editURL" /> <a class="btn btn-info" 
								href="${editURL}" role="button">Update</a></td>
				<td><spring:url value="/${academy.code}/student/remove/${student.fiscalCode}"
								var="deleteURL" /> <a class="btn btn-danger"
								href="${deleteURL}" role="button">Delete</a></td>
			</tr>
		</tbody>
	</table>
	&emsp;&emsp;
			<spring:url value="/home" var="homeURL" />
		
			<a class="btn btn-light" href="${homeURL}" role="button">
			
			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
  			<path fill-rule="evenodd" d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
  			<path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
	</svg></a>
		&emsp;&emsp;
			<spring:url value="/${academy.code }/students" var="studsURL" />
			<a class="btn btn-light" href="${studsURL}" role="button">
			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-arrow-left" viewBox="0 0 16 16">
  			<path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
</svg></a>
	</div>
</body>
</html>