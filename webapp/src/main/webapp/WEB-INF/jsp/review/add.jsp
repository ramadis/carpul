<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title><spring:message code="review.add.page_title" /></title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/trip-form.css' />" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
    <script src="<c:url value='/static/js/review_stars.js' />" charset="utf-8"></script>
  </head>
  <body>
    <spring:message code="review.add.hero" var="hero_message" />
    <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

    <div class="profile-form-container flex-center">
      <form:form class="new-trip-form" modelAttribute="reviewForm" action="/webapp/review/${trip.id}">
        <h3><spring:message code="review.add.title"/></h3>
        <h2><spring:message code="review.add.subtitle" arguments="${user.first_name},${reviewed.first_name},${trip.from_city},${trip.to_city}"/></h2>

        <div class="field-container">
          <form:label path="message" class="field-label" for="message"><spring:message code="review.add.message" /></form:label>
          <div id="stars" class="stars"></div>
          <form:input required="required" class="field hide" name="stars" value="0" readonly="true" path="stars" min="0" max="5" type="number" />
          <form:textarea class="field" required="true" multiline="true" name="message" path="message" type="text" />
          <form:errors path="message" class="form-error" element="p"/>
        </div>

        <div class="actions">
          <button type="submit" class="login-button"><spring:message code="review.add.submit" /></button>
        </div>
      </form:form>
    </div>
  </body>
</html>
