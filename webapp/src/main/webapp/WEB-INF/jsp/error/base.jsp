<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul | Yikes!</title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/error.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body class="flex-center">
    <h1 class="error-title">So... this is awkward.</h1>
    <h1 class="error-subtitle">(yep, this is a ${error} error page)</h1>
  </body>
</html>
