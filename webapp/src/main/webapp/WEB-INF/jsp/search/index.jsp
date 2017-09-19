<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul - Search</title>
  </head>
  <body>
    <c:if test="${not empty trips}">
      <c:forEach items="${trips}" var="trip">
        ${trip.id}
        ${trip.etd}
        ${trip.eta}
        <c:forEach items="${trip.passengerIds}" var="passengerId">
          ${passengerId}
        </c:forEach>
      </c:forEach>
    </c:if>
  </body>
</html>
