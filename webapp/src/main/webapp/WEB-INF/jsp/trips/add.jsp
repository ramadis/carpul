<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul | Add a trip </title>

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
    <c:set var="hero_message" value="Where are you going next?"/>
    <%@ include file="../common/navbar.jsp" %>
    <%@ include file="../common/hero.jsp" %>

    <div class="profile-form-container flex-center">
      <form:form method="post" class="new-trip-form" modelAttribute="tripForm" action="trip">
        <h3>Add a new trip</h3>

        <div id="map" style="width=100%; height: 400px; margin: 10px 0;"></div>

        <div class="field-container">
          <form:label path="from_city" class="field-label" for="from_city">Departure city</form:label>
          <form:input required="true" readonly="true" class="field" path="from_city" type="text" name="from_city"/>
          <form:errors path="from_city" class="form-error" element="p"/>

          <form:label path="to_city" class="field-label" for="to_city">Arrival city</form:label>
          <form:input required="true" readonly="true" class="field" path="to_city" type="text" name="to_city"/>
          <form:errors path="to_city" class="form-error" element="p"/>

          <form:label path="seats" class="field-label" for="seats">Available seats</form:label>
          <form:input required="true" min="1" max="20" class="field" path="seats" type="number" name="seats"/>
          <form:errors path="seats" class="form-error" element="p"/>

          <form:label path="cost" class="field-label" for="cost">Total cost</form:label>
          <span class="cost-field"><form:input required="true" min="0" max="10000" class="field" path="cost" type="number" name="cost"/></span>
          <form:errors path="cost" class="form-error" element="p"/>

          <form:label path="etd" class="field-label" for="etd">Date and time of departure</form:label>
          <input required id="etd" class="field" type="text" name="etd_mask"/>
          <form:input required="true" min="1" max="20" class="field hide" path="etd" type="text" name="etd"/>
          <form:errors path="etd" class="form-error" element="p"/>

          <form:label path="eta" class="field-label" for="eta">Estimated date and time of departure</form:label>
          <input required id="eta" class="field" type="text" name="eta_mask"/>
          <form:input required="true" min="1" max="20" class="field hide" path="eta" type="text" name="eta"/>
          <form:errors path="eta" class="form-error" element="p"/>
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
