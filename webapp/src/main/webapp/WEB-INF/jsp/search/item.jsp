<c:set var="url" value=""/>
<c:if test="${is_searching}">
  <c:set var="url" value="../"/>
</c:if>

<div class="pool-item flex-center">
  <div class="user-info flex space-around align-center column h-150">
    <div class="user-image">
      <img src="https://ui-avatars.com/api/?rounded=true&size=85&background=e36f4a&color=fff&name=${trip.driver.first_name} ${trip.driver.last_name}" alt="">
    </div>
    <div class="user-name">
      ${trip.driver.first_name}
    </div>
    <%-- <span class="user-rating">
      <img src="<c:url value='/static/images/star.png' />"/>
      <img src="<c:url value='/static/images/star.png' />"/>
      <img src="<c:url value='/static/images/star.png' />"/>
      <img src="<c:url value='/static/images/star.png' />"/>
      <img src="<c:url value='/static/images/star.png' />"/>
    </span> --%>
  </div>

  <div class="pool-info">
    <div class="map-container">
      <img src="https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA&size=1200x200&markers=color:green|label:A|${trip.departure.latitude}, ${trip.departure.longitude}&markers=color:blue|label:B|${trip.arrival.latitude}, ${trip.arrival.longitude}&path=color:0x0000ff80|weight:1|${trip.arrival.latitude}, ${trip.arrival.longitude}|${trip.departure.latitude}, ${trip.departure.longitude}" style="width: 100%; height: 100%;"></img>
    </div>
    <div class="bg-white">
      <div class="price-container flex space-between align-center">
        <span class="clear gray sz-13">
          <spring:message code="search.item.from"/>
          <span class="bold black"> ${trip.from_city}</span>
          <spring:message code="search.item.on_low"/>
          <span class="bold black"> <fmt:formatDate value="${trip.etd}" pattern="dd/MM/yyyy"/></span>
          <spring:message code="search.item.at"/>
          <span class="bold black"> <fmt:formatDate value="${trip.etd}" pattern="HH:mm"/></span>
          <br>
          <spring:message code="search.item.arrive"/>
          <span class="bold black"> ${trip.to_city}</span>
          <spring:message code="search.item.on_low"/>
          <span class="bold black"> <fmt:formatDate value="${trip.eta}" pattern="dd/MM/yyyy"/></span>
          <spring:message code="search.item.at"/>
          <span class="bold black"> <fmt:formatDate value="${trip.eta}" pattern="HH:mm"/></span>
        </span>
        <div>
          <span class="price gray">
            <span class="bold black">$${trip.cost}</span>
            /<spring:message code="search.item.each"/>
          </span>

            <c:if test="${not trip.reserved}">
              <form:form class="inline-block" method="post" action="${url}trip/${trip.id}/reserve">
                <button class="login-button"><spring:message code="search.item.reserve"/></button>
              </form:form>
            </c:if>
            <c:if test="${trip.reserved}">
              <form:form class="inline-block" method="post" action="${url}trip/${trip.id}/unreserve">
                <button class="login-button main-color"><spring:message code="search.item.unreserve"/></button>
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
            ${trip.available_seats} <spring:message code="search.item.available"/>
          </span>
        </div>
      </div>
    </div>
  </div>
</div>
