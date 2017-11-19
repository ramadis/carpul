<li class="destiny-item" data-id="${reservation.id}">
  <fmt:formatDate value="${reservation.etd}" var="fmtetddate" pattern="dd/MM/yyyy"/>
  <fmt:formatDate value="${reservation.etd}" var="fmtetdtime" pattern="HH:mm"/>
  <fmt:formatDate value="${reservation.eta}" var="fmtetadate" pattern="dd/MM/yyyy"/>
  <fmt:formatDate value="${reservation.eta}" var="fmtetatime" pattern="HH:mm"/>

  <form:form class="inline-block no-margin" method="post" action="../trip/${reservation.id}/unreserve">
    <span class="destiny-cost"><span class="bold" style="display: inline;">$${reservation.cost}</span></span>
    <span class="destiny-name">${reservation.to_city}</span>
    <span class="destiny-time"><spring:message code="user.trip.departing" arguments="${reservation.from_city}"/></span>
    <div class="destiny-timetable">
      <div class="destiny-timerow">
        <span class="destiny-time-titlespan"><spring:message code="user.trip.depart_single"/></span>
        <span>${fmtetddate}</span>
        <span class="destiny-time-span">${fmtetdtime}</span>
      </div>
      <div class="destiny-timerow">
        <span class="destiny-time-titlespan"><spring:message code="user.trip.arrive_single"/></span>
        <span>${fmtetadate}</span>
        <span class="destiny-time-span">${fmtetatime}</span>
      </div>
    </div>
    <a class="destiny-time map-trigger" target="iframe" href="https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${reservation.departure.latitude}, ${reservation.departure.longitude}&destination=${reservation.arrival.latitude}, ${reservation.arrival.longitude}" >See adventure on the map</a>
    <button class="destiny-unreserve-button" onclick="unreserve(${trip.id})"><spring:message code="user.trip.unreserve"/></button>
    <hr>
    <div class="driver">
      <div class="driver-item-data">
        <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${reservation.driver.first_name} ${reservation.driver.last_name}" alt="">
        <a href="<c:url value='/user/${reservation.driver.id}' />">
          <div class="driver-info">
            <span class="driver-name">${reservation.driver.first_name} ${reservation.driver.last_name}</span>
            <span>${reservation.driver.phone_number}</span>
          </div>
        </a>
      </div>
    </div>
  </form:form>
</li>
