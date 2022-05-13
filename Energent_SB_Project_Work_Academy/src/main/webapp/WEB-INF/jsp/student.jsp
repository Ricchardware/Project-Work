<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Student</title>
<link href="../../webjars/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="../../webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="../../webjars/jquery/3.0.0/js/jquery.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body style="background-color: #333333">
	</br>
	</br>
	<font color="white">
		<div class="container">
			<c:set var="fiscalcode" value="${student.fiscalCode}" />
			<c:if test="${fiscalCode == null}">
				<h2>Add Student</h2>
				<spring:url value="/${academy.code}/student/add" var="addURL" />
			</c:if>
			<c:if test="${fiscalCode != null}">
				<spring:url value="/${academy.code}/student/update/${student.fiscalCode}" var="addURL" />
				<h2>Update Student</h2>
			</c:if>
			<!--ModelAttribute=n collegamento tra model e view     -->
			<form:form modelAttribute="student" method="post" action="${addURL}"
				cssClass="form">
				<div class="form-group">
					<label>First Name</label>
					<form:input path="firstName" cssClass="form-control" id="firstName"
						autocomplete="off" style="background-color:#18191A; color:white" required="required"/>
				</div>
				<div class="form-group">
					<label>Last Name</label>
					<form:input path="lastName" cssClass="form-control" id="lastName"
						autocomplete="off" style="background-color:#18191A; color:white" required="required"/>
				</div>
				<c:if test="${fiscalCode == null}">
				<div class="form-group">
					<label>Fiscal Code</label>
					<form:input path="fiscalCode" cssClass="form-control"
						id="fiscalCode" autocomplete="off"
						style="background-color:#18191A; color:white" required="required" />
				</div>
				</c:if>
				<c:if test="${fiscalCode != null}">
				<div class="form-group">
					<label>Fiscal Code</label>
					<form:input path="fiscalCode" cssClass="form-control"
						id="fiscalCode" autocomplete="off"
						style="background-color:#18191A; color:#bebebe" required="required" readonly="true"/>
				</div>
				</c:if>
				<div class="form-group">
					<label>Age</label>
					<form:input path="age" cssClass="form-control" id="age"
						autocomplete="off" type="number" style="background-color:#18191A; color:white" required="required"/>
				</div>
				<div class="form-group">
				<c:if test="${fiscalCode == null}">
				<button href="addURL" type="submit" class="btn btn-success">Add Student</button>
				</c:if>
			<c:if test="${fiscalCode != null}">
				<button href="addURL" type="submit" class="btn btn-info">Update Student</button>
			</c:if>
			</form:form>
			
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
<c:set var="situation" value="${situation}" />
<c:if test="${situation == 1}">
				<font size="3" color="red"> &emsp; You must fill all forms </font>
			</c:if>
<c:if test="${situation == 3}">
				<font size="3" color="red"> &emsp; The minimum age is 6 </font>
			</c:if>
			
			<c:if test="${situation == 4}">
				<font size="3" color="red"> &emsp; No Special Characters </font>
			</c:if>
		</div>
</body>
</html>