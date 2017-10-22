<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title><spring:message code="error.base.page_title" /></title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/error.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body class="flex-center">
    <h1 class="error-title"><spring:message code="error.base.title" /></h1>
    <h1 class="error-subtitle"><spring:message code="error.base.subtitle" arguments="${error}"/></h1>
  </body>
</html>
