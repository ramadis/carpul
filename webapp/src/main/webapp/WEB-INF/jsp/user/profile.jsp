<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
  <link type="image/x-icon" rel="shortcut icon" href="<c:url value='/static/images/favicon.ico' />" >
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta charset="UTF-8">
  <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/review_item.css' />" rel="stylesheet" type="text/css" />

  <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
  <script type="text/javascript">
    function deleteTrip(id) {
      // TODO: Check if should reload on callback.
      var confirmate = confirm('Are you sure you want to delete this trip?');
      if (confirmate) {
        $.post('../trip/' + id + '/delete');
        location.reload();
      }
    }

    function kickout(id, tripId) {
      var confirmate = confirm('Are you sure you want to remove this passenger from you trip?');
      if (confirmate) {
        $.post('../trip/' + tripId + '/unreserve/' + id);
        location.reload();
      }
    }

    function unreserve(id) {
      var confirmate = confirm('Are you sure you want to delete this trip?');
      if (confirmate) {
        $.post('../trip/' + id + '/unreserve');
        location.reload();
      }
    }
  </script>
  <title><spring:message code="user.profile.page_title" arguments="${user.first_name},${user.last_name}"/></title>
</head>
<body>
  <spring:message code="user.profile.hero" var="hero_message" />
  <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
  <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

  <section class="profile-container">
    <c:if test="${not empty reservations or not empty trips or not empty reviews or not empty histories}">

      <section class="reviews-container">
        <c:if test="${not empty reviews}">
          <h3><spring:message code="user.profile.reviews"/></h3>

          <ul class="no-bullets destiny-list">
            <c:forEach items="${reviews}" var="review">
              <%@ include file="../review/item.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
        <c:if test="${empty reviews}">
          <h3><spring:message code="user.profile.empty_review"/></h3>
        </c:if>
      </section>

      <section class="destinys-container">
        <c:if test="${not empty histories}">
          <h3><spring:message code="user.profile.history"/></h3>

          <ul class="no-bullets destiny-list">
            <c:forEach items="${histories}" var="history">
              <%@ include file="../history/item.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
        <c:if test="${empty histories}">
          <h3><spring:message code="user.profile.empty_histories"/></h3>
        </c:if>
      </section>

      <section class="destinys-container">
        <h3><spring:message code="user.profile.next"/></h3>

        <a class="no-margin login-button" href="<c:url value='/' />"><spring:message code="user.profile.find"/></a>

        <c:if test="${not empty reservations}">
          <ul class="no-bullets destiny-list">
            <c:forEach items="${reservations}" var="reservation">
              <c:if test="${not reservation.expired}">
                <%@ include file="destiny.jsp" %>
              </c:if>
              <c:if test="${reservation.expired}">
                <%@ include file="trip-past.jsp" %>
              </c:if>
            </c:forEach>
          </ul>
        </c:if>
        <c:if test="${empty reservations}">
          <h3 class="empty-message"><spring:message code="user.profile.empty_reservations"/></h3>
        </c:if>
      </section>

      <section class="destinys-container">
        <h3><spring:message code="user.profile.trips"/></h3>

        <a class="no-margin login-button" href="<c:url value='/trip' />"><spring:message code="user.profile.new"/></a>

        <c:if test="${not empty trips}">
          <ul class="no-bullets destiny-list">
            <c:forEach items="${trips}" var="trip">
              <%@ include file="trip.jsp" %>
            </c:forEach>
          </ul>
        </c:if>
        <c:if test="${empty trips}">
          <h3 class="empty-message"><spring:message code="user.profile.empty_trips"/></h3>
        </c:if>
      </section>
    </c:if>

    <c:if test="${empty reservations and empty trips and empty histories and empty reviews}">
      <div class="empty-profile">
        <h3 class="empty-title"><spring:message code="user.profile.empty_title"/></h3>
        <h4 class="empty-subtitle"><spring:message code="user.profile.empty_subtitle"/></h4>
        <a class="no-margin login-button empty-button" href="<c:url value='/trip' />"><spring:message code="user.profile.empty_new"/></a>
        <a class="no-margin login-button empty-button" href="<c:url value='/' />"><spring:message code="user.profile.empty_find"/></a>
      </div>

    </c:if>
  </section>
</body>
</html>
