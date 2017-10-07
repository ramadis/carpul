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
    <div class="navbar">
      <div class="top-section flex align-center">
        <img src="<c:url value='/static/images/logo.png' />" alt=""></img>
        <div class="actions">
          <c:if test="${empty user}">
            <a href="<c:url value='/user' />" class="create-account bold m-r-10" >Create account</a>
          </c:if>
          <c:if test="${not empty user}">
            <a href="<c:url value='/logout' />" class="create-account bold m-r-10" >Logout</a>
          </c:if>
          <c:if test="${empty user}">
            <a href="<c:url value='/login' />" class="login-button" >Login</a>
          </c:if>
          <c:if test="${not empty user}">
            <a href="<c:url value='/user/${user.id}' />" class="login-button" >Profile</a>
          </c:if>
        </div>
      </div>
    </div>

    <div class="profile-hero-container">
      <div class="profile-hero-alignment">
        <img src="https://ui-avatars.com/api/?rounded=true&size=100" alt="">
        <div class="profile-user-container">
          <span class="profile-user-name">${user.username}</span>
          <span class="profile-user-created">Have been sharing adventures for 2 years</span>
        </div>
      </div>
    </div>
    <div class="profile-hero-catchphrase">
      <span>Where are you going next?</span>
    </div>
    <div class="profile-hero-border" />

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
