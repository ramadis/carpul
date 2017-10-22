<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
  <link type="image/x-icon" rel="shortcut icon" href="<c:url value='/static/images/favicon.ico' />" >
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/review_item.css' />" rel="stylesheet" type="text/css" />

  <title><spring:message code="user.unauth.profile.page_title" arguments="${user.first_name},${user.last_name}"/></title>
</head>
<body>
  <spring:message code="user.unauth.profile.hero" arguments="${user.first_name}" var="hero_message" />
  <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
  <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

  <section class="profile-container">
    <c:if test="${not empty reservations or not empty trips or not empty reviews}">

      <section class="reviews-container">
        <c:if test="${not empty reviews}">
          <h3><spring:message code="user.unauth.profile.reviews" arguments="${user.first_name}"/></h3>

          <ul class="no-bullets destiny-list">
            <c:forEach items="${reviews}" var="review">
              <%@ include file="../review/item.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
        <c:if test="${empty reviews}">
          <h3><spring:message code="user.unauth.profile.empty_review" arguments="${user.first_name}"/></h3>
        </c:if>
      </section>

      <section class="destinys-container">
        <h3><spring:message code="user.profile.next"/></h3>

        <a class="no-margin login-button" href="<c:url value='/trip' />"><spring:message code="user.profile.new"/></a>

        <c:if test="${not empty trips}">
          <ul class="no-bullets destiny-list">
            <c:forEach items="${trips}" var="trip">
              <a href="<c:url value='/search/${trip.id}?from=${trip.from_city}&to=${trip.to_city}&when=${trip.etd}' />"><%@ include file="trip.jsp" %></a>
            </c:forEach>
          </ul>
        </c:if>
      </section>
    </c:if>

    <c:if test="${empty reservations and empty trips}">
      <div class="empty-profile">
        <h3 class="empty-title"><spring:message code="user.profile.empty_title"/></h3>
      </div>
    </c:if>
  </section>
</body>
</html>
