<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>
	<h1>Travel Application Fare Info</h1>
	</br>
	<a href="${contextPath}/travel/stats">Application statisticst</a>
	<a href="${contextPath}/travel">Search flight</a>
	</br>

	<form:form>
		<div>
			<table border="1">
				<caption>Your travel itinerary for
					${travel_fare.depPort.code}-${travel_fare.arrPort.name}</caption>
				</br>
				<tr align="left">
					<th width="%40">Departure</th>
					<th width="%40">Arrival</th>
					<th width="%20">Fare</th>
				</tr>

				<tr>
					<td>
						<div>${travel_fare.depPort.code}-
							${travel_fare.depPort.name}</div>
						<div>${travel_fare.depPort.description}</div>
						<div>${travel_fare.depPort.coordinates.latitude}-
							${travel_fare.depPort.coordinates.longitude}</div>
					</td>

					<td>
						<div>${travel_fare.arrPort.code}-${travel_fare.arrPort.name}</div>
						<div>${travel_fare.arrPort.description}</div>
						<div>${travel_fare.arrPort.coordinates.latitude}-
							${travel_fare.arrPort.coordinates.longitude}</div>
					</td>

					<td align="center">
						<div>${travel_fare.fare.amount}${travel_fare.fare.currency}</div>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
</html>