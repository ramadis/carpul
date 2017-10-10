<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Carpul - Busqueda de viajes</title>
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
      <span class="list-subtitle">These are the options to go to ${to} this weekend</span>

      <c:if test="${not empty trips}">
        <c:forEach items="${trips}" var="trip">
          <!-- aca va el ng-repeat -->
          <div class="pool-item flex-center">
            <div class="user-info flex space-around align-center column h-150">
              <div class="user-image">
                <img src="https://ui-avatars.com/api/?rounded=true&size=85&background=e36f4a&color=fff&name=${trip.driver.first_name} ${trip.driver.last_name}" alt="">
              </div>
              <div class="user-name">
                ${trip.driver.first_name}
              </div>
              <span class="user-rating">
                <img src="<c:url value='/static/images/star.png' />"/>
                <img src="<c:url value='/static/images/star.png' />"/>
                <img src="<c:url value='/static/images/star.png' />"/>
                <img src="<c:url value='/static/images/star.png' />"/>
                <img src="<c:url value='/static/images/star.png' />"/>

              </span>
            </div>

            <div class="pool-info">
              <!-- aca iría el mapa arre -->
              <div class="map-container">
                <img src="https://puu.sh/xH5mj/28cb5c7eb2.png" style="width: 100%; height: 100%;"></img>
              </div>
              <div class="bg-white">
                <div class="price-container flex space-between align-center">
                  <span class="clear gray sz-13">
                    Leave from
                    <span class="bold black"> ${from}</span>
                    at
                    <span class="bold black"> ${trip.etd}</span>
                    arrive on
                    <span class="bold black"> ${to}</span>
                    at
                    <span class="bold black"> ${trip.eta}</span>
                  </span>
                  <div>
                    <span class="price gray">
                      <span class="bold black">$${trip.cost}</span>
                      /each
                    </span>
                      <c:if test="${not trip.reserved}">
                        <form:form class="inline-block" method="post" action="reserve/${trip.id}?from=${from}&to=${to}">
                          <button class="login-button">Reserve</button>
                        </form:form>
                      </c:if>
                      <c:if test="${trip.reserved}">
                        <form:form class="inline-block" method="post" action="unreserve/${trip.id}?from=${from}&to=${to}">
                          <button class="login-button main-color">Unreserve</button>
                        </form:form>
                      </c:if>
                  </div>
                </div>

                <div class="pool-features flex space-between align-center">
                  <div class="features-container">
                  </div>
                  <div class="seats-container">
                    <span class="seats bold gray">
                      <img class="seats-icon" src="<c:url value='/static/images/seats.png' />"></img>
                      3 available
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:if>



    </div>
  </body>
</html>
