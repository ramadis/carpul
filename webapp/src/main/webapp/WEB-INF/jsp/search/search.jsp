<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title><spring:message code="search.search.page_title" arguments="${search.to}" /></title>
    <link type="image/x-icon" rel="shortcut icon" href="<c:url value='/static/images/favicon.ico' />" >
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body >
    <fmt:formatDate var="fmtwhen" value="${search.when}" pattern="dd/MM/yyyy"/>

    <div class="navbar">
      <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>

      <div class="destination flex align-center">
        <div class="destination-container">
          <span class="bold m-r-5"><spring:message code="search.search.from"/></span>
          <span class="clear">${search.from}</span>
        </div>
        <div class="destination-container">
          <span class="bold m-r-5"><spring:message code="search.search.to"/></span>
          <span class="clear">${search.to}</span>
        </div>
        <div class="destination-container">
          <span class="bold m-r-5"><spring:message code="search.search.on"/></span>
          <span class="clear"><fmt:formatDate value="${search.when}" pattern="dd/MM/yyyy"/></span>
        </div>
      </div>
    </div>

    <div class="list-container">
      <c:if test="${not empty trips}">
        <span class="list-subtitle"><spring:message code="search.search.trips" arguments="${search.to},${fmtwhen}" /></span>
        <c:forEach items="${trips}" var="trip">
          <%@ include file="item.jsp" %>
        </c:forEach>
      </c:if>

      <c:if test="${not empty later_trips}">
        <span class="list-subtitle"><spring:message code="search.search.later_trips" arguments="${search.to},${fmtwhen}" /></span>
        <c:forEach items="${later_trips}" var="trip">
          <%@ include file="item.jsp" %>
        </c:forEach>
      </c:if>

      <c:if test="${empty trips and empty later_trips}">
        <span class="list-subtitle"><spring:message code="search.search.no_trips" arguments="${search.to}" /></span>
      </c:if>



    </div>
  </body>
</html>
