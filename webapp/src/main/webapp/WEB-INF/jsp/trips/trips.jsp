<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>


<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul - Log in</title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile_hero.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/profile.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>
    <c:set var="hero_message" value="Where are you going next?"/>
    <%@ include file="/WEB-INF/jsp/common/hero.jsp" %>

    <div class="profile-form-container">
      <form:form method="post" modelAttribute="tripForm" action="/webapp/trip">
            <div class="field-container">
              <spring:bind path="cost">
                  <label class="field-label" for="cost">Cost</label>
                  <input class="field" name="cost" path="cost" type="number" />
              </spring:bind>
              <spring:bind path="etd">
                <label class="field-label" for="etd">Estimated time of departure</label>
                <input class="field" path="etd" type="text" name="etd"/>
              </spring:bind>
              <spring:bind path="eta">
                <label class="field-label" for="eta">Estimated time of arrival</label>
                <input class="field" path="eta" type="text" name="eta"/>
              </spring:bind>
            </div>
            <div class="actions">
              <button type="submit" class="login-button">Add destiny</button>
            </div>
      </form:form>
    </div>
  </body>
</html>
