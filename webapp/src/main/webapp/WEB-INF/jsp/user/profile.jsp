<%-- Comments so as to know from where the variables come from --%>
<%--@elvariable id="user" type="ar.edu.itba.paw.models.User"--%>
<%--@elvariable id="greeting" type="ar.edu.itba.paw.webapp.controllers.HelloWorldController"--%>

<%--
    This was the one Sotuyo gave us. The second one is the recommended one.
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />

<title>Carpul | ${user.first_name} ${user.last_name} is awesome</title>
</head>
<body>
  <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
  <c:set var="hero_message" value="So, this is your place. Feel like home."/>
  <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

  <section class="profile-container">
    <section class="reviews-container">
    </section>

    <section class="destinys-container">
      <h3>What's next</h3>

      <a class="no-margin login-button" href="<c:url value='/trip' />">Driving somewhere? Add a new destiny</a>

      <c:if test="${not empty reservations}">
        <ul class="no-bullets destiny-list">
          <c:forEach items="${reservations}" var="reservation">
            <li class="destiny-item" data-id="${reservation.id}">
              <form:form class="inline-block no-margin" method="post" action="../unreserve/${reservation.id}">
                <span class="destiny-cost"><span class="bold" style="display: inline;">$${reservation.cost}</span> or less</span>
                <span class="destiny-name">${reservation.to_city}</span>
                <span class="destiny-time">Depart from ${reservation.from_city} on {Sunday} at ${reservation.etd}</span>
                <span class="destiny-time">Arrive on ${reservation.to_city} on {Sunday} at ${reservation.eta}</span>
                <button class="destiny-unreserve-button">Unreserve</button>
                <hr>
                <div class="driver">
                  <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${reservation.driver.first_name} ${reservation.driver.last_name}" alt="">
                  <div class="driver-info">
                    <span class="driver-name">${reservation.driver.first_name} ${reservation.driver.last_name}</span>
                    <span>${reservation.driver.phone_number}</span>
                  </div>
                </div>

              </form:form>
            </li>
          </c:forEach>
        </ul>
      </c:if>
    </section>
  </section>

  <c:if test="${not empty trips}">
    <ul>
      <c:forEach items="${trips}" var="trip">
        <li>${trip.etd} - ${trip.eta} for $${trip.cost}</li>
      </c:forEach>
    </ul>
  </c:if>


</body>
</html>
