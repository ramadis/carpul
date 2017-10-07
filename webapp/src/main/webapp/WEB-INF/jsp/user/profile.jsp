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
<title>HelloWorldJSP</title>
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

  <c:if test="${not empty trips}">
    <ul>
      <c:forEach items="${trips}" var="trip">
        <li>${trip.etd} - ${trip.eta} for $${trip.cost}</li>
      </c:forEach>
    </ul>
  </c:if>
  <a class="login-button" href="<c:url value='/trip' />">Add destiny</a>
</body>
</html>
