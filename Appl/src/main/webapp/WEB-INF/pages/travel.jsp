<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>
	<h1>Travel Application</h1>
	</br>
	<a href="${contextPath}/travel/stats">Application statisticst</a>
	</br>

	<form:form method="POST" action="/travel/fare">
		<div>
			<span id="dep_port_span"> <label for="default">Departure</label>
				<input type="text" id="default" list="dep_ports" width="300px"
				name="dep_port">
			</span>

			<datalist id="dep_ports">
				<c:forEach items="${ports}" var="port">
					<option value="${port.code}">${port.name} -
						${port.description}
					<option />
				</c:forEach>
			</datalist>

			<span id="arr_port_span"> <label for="default">Arrival</label>
				<input type="text" id="default" list="arr_ports" width="300px"
				name="arr_port"> <datalist id="arr_ports">
					<c:forEach items="${ports}" var="port">
						<option value="${port.code}">${port.name} -
							${port.description}
						<option />
					</c:forEach>
				</datalist>
			</span>
			<button id="search" name="search" onclick="submit()">search</button>

		</div>
	</form:form>
</body>
</html>