<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul | Leave your mark </title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/trip-form.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
    <c:set var="hero_message" value="How was your trip?"/>
    <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

    <div class="profile-form-container flex-center">
      <form:form class="new-trip-form" modelAttribute="reviewForm" action="/webapp/review/${trip.id}">
        <h3>Add a new review</h3>
        <h2>${owner.first_name}, leave a message for future travelers. How was your trip with ${user.first_name} from ${trip.from_city} to ${trip.to_city}?</h2>
        <div class="field-container">
          <spring:bind path="stars">
              <label class="field-label" for="stars">Rate your trip from 1 to 5</label>
              <form:input required="required" class="field" name="stars" path="stars" min="0" max="5" type="number" />
          </spring:bind>

          <spring:bind path="message">
              <label class="field-label" for="message">Leave a message for next travelers</label>
              <form:input class="field" name="message" path="message" type="text" />
          </spring:bind>
        <div class="actions">
          <button type="submit" class="login-button">Leave your mark</button>
        </div>
      </form:form>
    </div>
  </body>
</html>
