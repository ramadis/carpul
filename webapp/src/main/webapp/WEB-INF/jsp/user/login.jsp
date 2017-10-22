<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>


<html>
  <head>
    <meta charset="utf-8">
    <title><spring:message code="user.login.page_title"/></title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body class="flex-center">

    <form:form method="post" modelAttribute="userForm" action="${loginUserURI}" class="user-form">
        <div class="top-border"></div>
        <div class="text-container">
          <span>carpul</span>
          <span class="catchphrase"><spring:message code="user.login.title"/></span>
          <span class="catchphrase-description"><spring:message code="user.login.subtitle1"/></span>
          <span class="catchphrase-description"> <spring:message code="user.login.subtitle2"/></span>
        </div>
          <div class="field-container">
            <spring:bind path="username">
                <label class="field-label" for="username"><spring:message code="user.login.username"/></label>
                <input class="field" name="username" path="username" type="text" />
            </spring:bind>
            <spring:bind path="password">
              <label class="field-label" for="password"><spring:message code="user.login.password"/></label>
              <input class="field" path="password" type="password" name="password"/>
            </spring:bind>
          </div>
          <div class="actions">
            <a href="<c:url value='${registerUserURI}'/>" class="create-account"><spring:message code="user.login.create"/></a>
            <button type="submit" class="login-button"><spring:message code="user.login.submit"/></button>
          </div>
    </form:form>
  </body>
</html>
