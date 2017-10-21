<li class="destiny-item trip-item" data-id="${trip.id}">
  <form:form class="inline-block no-margin" method="post" action="../trip/${trip.id}/delete">
    <c:if test="${trip.occupied_seats eq 0}">
      <span class="destiny-cost">Earning <span class="bold" style="display: inline;">nothing yet</span></span>
    </c:if>
    <c:if test="${trip.occupied_seats ne 0}">
      <span class="destiny-cost">Earning <span class="bold" style="display: inline;">$${trip.cost}</span></span>
    </c:if>
    <span class="destiny-name">${trip.to_city}</span>
    <span class="destiny-time">Depart from ${trip.from_city} on <fmt:formatDate value="${trip.etd}" pattern="dd/MM/yyyy"/> at <fmt:formatDate value="${trip.etd}" pattern="HH:mm"/></span>
    <span class="destiny-time">Arrive on ${trip.to_city} on <fmt:formatDate value="${trip.eta}" pattern="dd/MM/yyyy"/> at <fmt:formatDate value="${trip.eta}" pattern="HH:mm"/></span>
    <a class="destiny-time map-trigger" target="iframe" href="https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${trip.departure.latitude}, ${trip.departure.longitude}&destination=${trip.arrival.latitude}, ${trip.arrival.longitude}" >See adventure on the map</a>
    <button class="destiny-unreserve-button">Delete trip</button>
    <c:if test="${not empty trip.passengers}">
      <hr>
      <c:forEach items="${trip.passengers}" var="passenger">
        <a href="<c:url value='/user/${passenger.id}' />">
          <div class="driver">
            <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${passenger.first_name} ${passenger.last_name}" alt="">
            <div class="driver-info">
              <span class="driver-name">${passenger.first_name} ${passenger.last_name}</span>
              <span>${passenger.phone_number}</span>
            </div>
          </div>
        </a>
      </c:forEach>
    </c:if>
  </form:form>
</li>
