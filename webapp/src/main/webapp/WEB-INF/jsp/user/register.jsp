<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title><spring:message code="user.register.page_title"/></title>
    <link type="image/x-icon" rel="shortcut icon" href="<c:url value='/static/images/favicon.ico' />" >
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body class="flex-center">

    <form:form method="post" modelAttribute="userCreateForm" action="${registerUserURI}" class="user-form">
        <div class="top-border"></div>

        <div class="text-container">
          <span>carpul</span>
          <span class="catchphrase"><spring:message code="user.register.title"/></span>
          <span class="catchphrase-description"><spring:message code="user.register.subtitle"/></span>
        </div>

        <div class="field-container">
          <form:label path="first_name" class="field-label" for="first_name"><spring:message code="user.register.first_name"/></form:label>
          <form:input required="true" class="field" path="first_name" type="text" name="first_name"/>
          <form:errors path="first_name" class="form-error" element="p"/>

          <form:label path="last_name" class="field-label" for="last_name"><spring:message code="user.register.last_name"/></form:label>
          <form:input required="true" class="field" path="last_name" type="text" name="last_name"/>
          <form:errors path="last_name" class="form-error" element="p"/>


          <form:label path="phone_number" class="field-label" for="phone_number"><spring:message code="user.register.phone_number"/></form:label>
          <form:input required="true" class="field" path="phone_number" type="text" name="phone_number"/>
          <form:errors path="phone_number" class="form-error" element="p"/>

          <form:label path="username" class="field-label" for="username"><spring:message code="user.register.username"/></form:label>
          <form:input required="true" class="field" name="username" path="username" type="text" />
          <form:errors path="username" class="form-error" element="p"/>

          <form:label path="password" class="field-label" for="password"><spring:message code="user.register.password"/></form:label>
          <form:input required="true" class="field" pattern=".{6,}" path="password" type="text" name="password"/>
          <form:errors path="password" class="form-error" element="p"/>
        </div>

        <div class="actions">
          <a href="<c:url value='${loginUserURI}'/>" class="create-account"><spring:message code="user.register.login"/></a>
          <button type="submit" class="login-button"><spring:message code="user.register.submit"/></button>
        </div>

        <form:errors path="" class="form-error" element="p"/>
    </form:form>
  </body>
</html>
