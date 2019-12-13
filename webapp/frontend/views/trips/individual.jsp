<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title><spring:message code="trip.individual.page_title"/></title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body >
    <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>

    <div class="list-container">

      <c:if test="${not empty trips}">
        <span class="list-subtitle"><spring:message code="trip.individual.title"/></span>
        <c:forEach items="${trips}" var="trip">
          <%@ include file="../search/item.jsp" %>
        </c:forEach>
      </c:if>

    </div>
  </body>
</html>
