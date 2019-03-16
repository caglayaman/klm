<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>
	<h1>Travel Application</h1>
	</br>

	<a href="${contextPath}/travel">Search flight</a>
	</br>

	<form:form>
		<div>
			<table border="1">
				<tr>
					<th>Total service call</th>
					<th>4XX errors</th>
					<th>5XX errors</th>
					<th>min response time</th>
					<th>max response time</th>
					<th>avarage response time</th>
				</tr>
				<tr>
					<td>${stats.totalCall}</td>
					<td>${stats.err400}</td>
					<td>${stats.err500}</td>
					<td>${stats.minTime}</td>
					<td>${stats.maxTime}</td>
					<td>${stats.avgTime}</td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
</html>