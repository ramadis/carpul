<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul | Welcome to a new adventure life</title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body class="flex-center">

    <form:form method="post" modelAttribute="userCreateForm" action="${registerUserURI}" class="user-form">
        <div class="top-border"></div>

        <div class="text-container">
          <span>carpul</span>
          <span class="catchphrase">Who are you?</span>
          <span class="catchphrase-description">Share something about you.</span>
        </div>

        <div class="field-container">
            <form:label path="first_name" class="field-label" for="first_name">First name</form:label>
            <form:input required="true" class="field" path="first_name" type="text" name="first_name"/>
            <form:errors path="first_name" class="form-error" element="p"/>

            <form:label path="last_name" class="field-label" for="last_name">Last name</form:label>
            <form:input required="true" class="field" path="last_name" type="text" name="last_name"/>
            <form:errors path="last_name" class="form-error" element="p"/>


            <form:label path="phone_number" class="field-label" for="phone_number">Phone number</form:label>
            <form:input required="true" class="field" path="phone_number" type="text" name="phone_number"/>
            <form:errors path="phone_number" class="form-error" element="p"/>

            <form:label path="username" class="field-label" for="username">Username</form:label>
            <form:input required="true" class="field" name="username" path="username" type="text" />
            <form:errors path="username" class="form-error" element="p"/>

            <form:label path="password" class="field-label" for="password">Password (At least 6 chars)</form:label>
            <form:input required="true" class="field" pattern=".{6,}" path="password" type="text" name="password"/>
            <form:errors path="password" class="form-error" element="p"/>

            <form:label path="repeat_password" class="field-label" for="repeat_password">Repeat password (At least 6 chars)</form:label>
            <form:input required="true" class="field" pattern=".{6,}" path="repeat_password" type="text" name="repeat_password"/>
            <form:errors path="repeat_password" class="form-error" element="p"/>

          </div>

          <div class="actions">
            <a href="<c:url value='${loginUserURI}'/>" class="create-account">Login</a>
            <button type="submit" class="login-button">Create account</button>
          </div>
    </form:form>
  </body>
</html>
