<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Carpul | Travel cheap to ${to}</title>
    <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  </head>
  <body >
    <div class="navbar">
      <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>

      <div class="destination flex align-center">
        <div class="destination-container">
          <span class="bold m-r-5">From</span>
          <span class="clear">${from}</span>
        </div>
        <div class="destination-container">
          <span class="bold m-r-5">To</span>
          <span class="clear">${to}</span>
        </div>
        <div class="destination-container">
          <span class="bold m-r-5">On</span>
          <select name="on" class="clear">
            <option value="0">This weekend</option>
          </select>
        </div>
      </div>
    </div>

    <div class="list-container">

      <c:if test="${not empty trips}">
        <span class="list-subtitle">These are the options to go to ${to} this weekend</span>
        <c:forEach items="${trips}" var="trip">
          <%@ include file="search_item.jsp" %>
        </c:forEach>
      </c:if>

      <c:if test="${empty trips}">
        <span class="list-subtitle">Sorry, no adventure to ${to} at this time :(</span>
      </c:if>



    </div>
  </body>
</html>
