<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Register Page</title>
<meta charset="UTF-8">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Title  -->
<title>HIS System - Health &amp; Medical Template | Contact</title>

<!-- Favicon  -->
<link rel="icon"
	href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

<!-- Style CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css">
</head>
<body>
	<%@ include file="header.jsp"%>
	<!-- ***** Breadcumb Area Start ***** -->
	<section class="breadcumb-area bg-img gradient-background-overlay"
		style="background-image: url(/img/bg-img/breadcumb3.jpg);">
		<div class="container h-100">
			<div class="row h-100 align-items-center">
				<div class="col-12">
					<div class="breadcumb-content">
						<h3 class="breadcumb-title">Update Plan Details</h3>
						<p>Update Exist Plan </p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- ***** Breadcumb Area End ***** -->
	<!-- ***** Book An Appoinment Area Start ***** -->
	<div class="medilife-book-an-appoinment-area">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md-12">
					<div class="medilife-appointment-form">
						<form:form action="/Admin/Plan/planFormProcess"
							modelAttribute="planDetails" method="POST">
							<form:hidden path="planId"/>
							<div class="form-group">
								<form:input path="planName"
									cssClass="form-control form-control border-top-1 border-right-1 border-left-1"
									id="name" placeholder="Plan Name" />
								<form:textarea path="discript"
									cssClass="form-control form-control border-top-1 border-right-1 border-left-1"
									id="name" placeholder="Description" />
								<div class="input-group-icon">
									<form:input path="startDate"
										cssClass="form-control form-control border-top-1 border-right-1 border-left-1 js-datepicker"
										placeholder="Plan Start Date" />
									<i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>
								</div>
								<div class="input-group-icon">
									<form:input path="endDate"
										cssClass="form-control form-control border-top-1 border-right-1 border-left-1 js-datepicker"
										id="name" placeholder="Plan End Date" />
									<i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>
								</div>
							</div>
							<div class="col-12 col-md-5 mb-0">
								<div class="form-group mb-0">
									<button type="submit" class="btn medilife-btn">
										Create Plan <span>+</span>
									</button>
								</div>
								</div>
						</form:form>

					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>

	<%@include file="footer.jsp"%>
	<!-- jQuery (Necessary for All JavaScript Plugins) -->
	<script
		src="${pageContext.request.contextPath}/js/jquery/jquery-2.2.4.min.js"></script>
	<!-- Popper js -->
	<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
	<!-- Bootstrap js -->
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datepicker/moment.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datepicker/daterangepicker.js"></script>
	<!-- Plugins js -->
	<script src="${pageContext.request.contextPath}/js/plugins.js"></script>
	<!-- Active js -->
	<script src="${pageContext.request.contextPath}/js/active.js"></script>
	<script src="${pageContext.request.contextPath}/js/global.js"></script>
</body>
</html>