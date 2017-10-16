<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul | Add a destiny </title>

    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/trip-form.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/datetime.component.css' />" rel="stylesheet" type="text/css" />

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="https://maps.google.com/maps/api/js?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA"></script>
    <script src="<c:url value='/static/js/gmaps.js' />" charset="utf-8"></script>
    <script src="<c:url value='/static/js/datetime.component.js' />" charset="utf-8"></script>
  </head>
  <body>
    <%@ include file="../common/navbar.jsp" %>
    <c:set var="hero_message" value="Where are you going next?"/>
    <%@ include file="../common/hero.jsp" %>

    <div class="profile-form-container flex-center">
      <form:form method="post" class="new-trip-form" modelAttribute="tripForm" action="trip">
        <h3>Add a new trip</h3>

        <div id="map" style="width=100%; height: 400px; margin: 10px 0;">

        </div>

        <div class="field-container">
          <spring:bind path="from_city">
              <label class="field-label" for="from_city">Departure city</label>
              <form:input required="required" class="field" readonly="true" name="from_city" path="from_city" min="0" type="text" />
          </spring:bind>

          <spring:bind path="to_city">
              <label class="field-label" for="to_city">Arrival city</label>
              <form:input required="required" readonly="true" class="field" name="to_city" path="to_city" min="0" type="text" />
          </spring:bind>

          <spring:bind path="seats">
              <label class="field-label" for="seats">Amount of free seats</label>
              <form:input required="required" min="1" class="field" name="seats" path="seats" type="number" />
          </spring:bind>

          <spring:bind path="cost">
              <label class="field-label" for="cost">Total cost</label>
              <span class="cost-field"><form:input required="required" class="field" name="cost" path="cost" min="0" type="number" /></span>
          </spring:bind>

          <spring:bind path="etd">
            <label class="field-label" for="etd_temp">Date and time of departure</label>
            <input required id="etd" class="field" type="text" name="etd_mask"/>
            <form:input class="field hide" path="etd_temp" type="text" name="etd_temp"/>
          </spring:bind>

          <spring:bind path="eta">
            <label class="field-label" for="eta_temp">Estimated Date and time of arrival</label>
            <input required id="eta" class="field" type="text" name="eta_mask"/>
            <form:input class="field hide" path="eta_temp" type="text" name="eta_temp"/>
          </spring:bind>
        </div>
        <div class="actions" style="margin-bottom: 10px;">
          <button type="submit" class="login-button">Start the adventure!</button>
        </div>
      </form:form>
    </div>

    <script src="<c:url value='/static/js/map.js' />" charset="utf-8"></script>
    <script src="<c:url value='/static/js/time.js' />" charset="utf-8"></script>
  </body>
</html>
