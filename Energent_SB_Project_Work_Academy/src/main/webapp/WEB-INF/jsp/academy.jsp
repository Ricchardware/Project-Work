<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Academy</title>
<link href="../../webjars/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/css/academy.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@300&family=Montserrat:wght@300&display=swap" rel="stylesheet">
<script src="../../webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="../../webjars/jquery/3.0.0/js/jquery.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body style="background-color:#333333">
	</br></br>
	<font color ="white">
	<div class="container">
	<c:set var="code" value="${academy.code}"/>  
	<c:if test="${code == null}">
		<h2>Add Academy</h2>
		<spring:url value="/academies/add" var="addURL" />
	</c:if>
	<c:if test="${code != null}">
		<spring:url value="/${academy.code}/update" var="addURL" />
		<h2>Update Academy</h2>
	</c:if>
		<!--ModelAttribute=n collegamento tra model e view     -->
		</br>
		<form:form modelAttribute="academy" method="post" action="${addURL}"
			cssClass="form"> 
			<c:if test="${code == null}">
			<div class="form-group">
				<label>Code</label>
				<form:input path="code" cssClass="form-control" id="code" autocomplete="off" style="background-color:#18191A; color:white" required="required"/>
			</div>
			</c:if>
			<c:if test="${code != null}">
			<div class="form-group">
				<label>Code</label>
				<form:input path="code" cssClass="form-control" id="code" autocomplete="off" style="background-color:#18191A; color:#bebebe" readonly="true" required="required"/>
			</div>
			</c:if>
			<div class="form-group">
				<label>Title</label>
				<form:input path="title" cssClass="form-control" id="title" autocomplete="off" style="background-color:#18191A; color:white" required="required"/>
			</div>
			<div class="form-group">
				<label>Location</label>
				<form:input path="location" cssClass="form-control" id="location" autocomplete="off" style="background-color:#18191A; color:white" required="required"/>
			</div>
			<div class="form-group">
				<label>Start Date</label>
				<form:input path="startDate" cssClass="form-control" id="startDate" type="date" style="background-color:#18191A; color:white; color-scheme: light dark;" required="required"/>
			</div>
			<div class="form-group">
				<label>End Date</label>
				<form:input path="endDate" cssClass="form-control" id="endDate" type="date" style="background-color:#18191A; color:white; color-scheme: light dark;" required="required"/>
			</div>
			<div class="form-group">
			<c:if test="${code == null}">
			<button type="button button-success" class="btn3d btn btn-default btn-lg" >Submit</button>
			<span class="glyphicon glyphicon-download-alt"></span>
			</c:if>
			<c:if test="${code != null}">
			<button type="submit" class="btn btn-info btn-lg btn3d">Update<span class="glyphicon glyphicon-question"></span></button>
			</c:if>
		</form:form>
		&emsp;&emsp;
			<spring:url value="/home" var="homeURL" />
		
			<a type="button" class="btn3d btn btn-white btn-lg" href="${homeURL}" role="button">
			
			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
  			<path fill-rule="evenodd" d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
  			<path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
	</svg></a>
	<c:set var="situation" value="${situation}"/>  
			<c:if test="${situation == 1 }">
			<font size="3" color="red"> &emsp; Fill all forms</font>
			</c:if>
			<c:if test="${situation == 2 }">
			<font size="3" color="red"> &emsp; You cannot set end date before start date</font>
			</c:if>
			<c:if test="${situation == 4}">
			<font size="3" color="red"> &emsp; No Special Characters </font>
			</c:if>

	</div>
</body>
</html>