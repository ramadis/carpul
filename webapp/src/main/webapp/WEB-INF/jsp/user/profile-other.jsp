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
  <c:set var="hero_message" value="Hey! Welcome to ${user.first_name} profile!"/>
  <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

  <section class="profile-container">
    <section class="reviews-container">
    </section>

    <section class="destinys-container">
      <h3>Upcoming ${user.first_name}'s trips</h3>

      <c:if test="${not empty trips}">
        <ul class="no-bullets destiny-list">
          <c:forEach items="${trips}" var="trip">
            <a href="<c:url value='/search/${trip.id}?from=${trip.from_city}&to=${trip.to_city}' />"><%@ include file="trip-unauth.jsp" %></a>
          </c:forEach>
        </ul>
      </c:if>
    </section>

  </section>




</body>
</html>
