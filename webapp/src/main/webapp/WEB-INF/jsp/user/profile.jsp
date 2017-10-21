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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/review_item.css' />" rel="stylesheet" type="text/css" />

<title>Carpul | ${user.first_name} ${user.last_name} is awesome</title>
</head>
<body>
  <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
  <c:set var="hero_message" value="So, this is your place. Feel like home."/>
  <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

  <section class="profile-container">
    <c:if test="${not empty reservations or not empty trips or not empty reviews}">

      <section class="reviews-container">
        <c:if test="${not empty reviews}">
          <h3>People are talking about you</h3>

          <ul class="no-bullets destiny-list">
            <c:forEach items="${reviews}" var="review">
              <%@ include file="../review/item.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
        <c:if test="${empty reviews}">
          <h3>People are not talking about you yet :(</h3>
        </c:if>
      </section>

      <section class="destinys-container">
        <c:if test="${not empty histories}">
          <h3>News about your trips!</h3>

          <ul class="no-bullets destiny-list">
            <c:forEach items="${histories}" var="history">
              <%@ include file="../history/item.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
      </section>

      <section class="destinys-container">
        <h3>What's next</h3>

        <a class="no-margin login-button" href="<c:url value='/' />">Going somewhere? Find a trip</a>

        <c:if test="${not empty reservations}">
          <ul class="no-bullets destiny-list">
            <c:forEach items="${reservations}" var="reservation">
              <%@ include file="trip-past.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
      </section>

      <section class="destinys-container">
        <h3>You're driving</h3>

        <a class="no-margin login-button" href="<c:url value='/trip' />">Take some people to a new destiny</a>

        <c:if test="${not empty trips}">
          <ul class="no-bullets destiny-list">
            <c:forEach items="${trips}" var="trip">
              <%@ include file="trip.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
      </section>
    </c:if>

    <c:if test="${empty reservations and empty trips}">
      <div class="empty-profile">
        <h3 class="empty-title">Seems like you don't have much to do here yet.</h3>
        <h4 class="empty-subtitle">Why not starting right now?</h4>
        <a class="no-margin login-button empty-button" href="<c:url value='/trip' />">Take some people to a new destiny</a>
        <a class="no-margin login-button empty-button" href="<c:url value='/' />">Find somewhere incredible to travel cheap</a>
      </div>

    </c:if>
  </section>




</body>
</html>
