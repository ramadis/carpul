<li class="destiny-item" data-id="${reservation.id}">
  <form:form class="inline-block no-margin" method="post" action="../trip/${reservation.id}/unreserve">
    <span class="destiny-cost"><span class="bold" style="display: inline;">$${reservation.cost_per_person}</span> or less</span>
    <span class="destiny-name">${reservation.to_city}</span>
    <span class="destiny-time">Depart from ${reservation.from_city} on <fmt:formatDate value="${reservation.etd}" pattern="dd/MM/yyyy"/> at <fmt:formatDate value="${reservation.etd}" pattern="HH:mm"/></span>
    <span class="destiny-time">Arrive on ${reservation.to_city} on <fmt:formatDate value="${reservation.eta}" pattern="dd/MM/yyyy"/> at <fmt:formatDate value="${reservation.eta}" pattern="HH:mm"/></span>
    <a class="destiny-time map-trigger" target="iframe" href="https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${reservation.departure.latitude}, ${reservation.departure.longitude}&destination=${reservation.arrival.latitude}, ${reservation.arrival.longitude}" >See adventure on the map</a>
    <button class="destiny-unreserve-button">Unreserve</button>
    <hr>
    <div class="driver">
      <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${reservation.driver.first_name} ${reservation.driver.last_name}" alt="">
      <a href="<c:url value='/user/${reservation.driver.id}' />">
        <div class="driver-info">
          <span class="driver-name">${reservation.driver.first_name} ${reservation.driver.last_name}</span>
          <span>${reservation.driver.phone_number}</span>
        </div>
      </a>
    </div>
  </form:form>
</li>
