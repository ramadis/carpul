<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul - Log in</title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body class="flex-center">

    <form:form method="post" modelAttribute="userForm" action="${registerUserURI}" class="user-form">
        <div class="top-border"></div>
        <div class="text-container">
          <span>carpul</span>
          <span class="catchphrase">Who are you?</span>
          <span class="catchphrase-description">Share something about you.</span>

        </div>
          <div class="field-container">
            <spring:bind path="first_name">
              <label class="field-label" for="first_name">First name</label>
              <input required class="field" path="first_name" type="text" name="first_name"/>
            </spring:bind>
            <spring:bind path="last_name">
              <label class="field-label" for="last_name">Last name</label>
              <input required class="field" path="last_name" type="text" name="last_name"/>
            </spring:bind>
            <spring:bind path="phone_number">
              <label class="field-label" for="phone_number">Phone number</label>
              <input required class="field" path="phone_number" type="text" name="phone_number"/>
            </spring:bind>
            <spring:bind path="username">
                <label class="field-label" for="username">Username</label>
                <input required class="field" name="username" path="username" type="text" />
            </spring:bind>
            <spring:bind path="password">
              <label class="field-label" for="password">Password (At least 6 chars)</label>
              <input required class="field" pattern=".{6,}" path="password" type="text" name="password"/>
            </spring:bind>
          </div>
          <div class="actions">
            <a href="<c:url value='${loginUserURI}'/>" class="create-account">Login</a>
            <button type="submit" class="login-button">Create account</button>
          </div>
    </form:form>
  </body>
</html>
