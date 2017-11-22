<li class="destiny-item trip-item" data-id="${trip.id}">
  <div class="inline-block no-margin">
    <c:if test="${trip.occupied_seats eq 0}">
      <span class="destiny-cost"><spring:message code="user.trip.earning"/> <span class="bold" style="display: inline;"><spring:message code="user.trip.nil"/></span></span>
    </c:if>
    <c:if test="${trip.occupied_seats ne 0}">
      <span class="destiny-cost"><spring:message code="user.trip.earning"/> <span class="bold" style="display: inline;">$${trip.cost * trip.occupied_seats}</span></span>
    </c:if>
    <span class="destiny-name">${trip.to_city}</span>

    <fmt:formatDate value="${trip.etd}" var="fmtetddate" pattern="dd/MM/yyyy"/>
    <fmt:formatDate value="${trip.etd}" var="fmtetdtime" pattern="HH:mm"/>
    <fmt:formatDate value="${trip.eta}" var="fmtetadate" pattern="dd/MM/yyyy"/>
    <fmt:formatDate value="${trip.eta}" var="fmtetatime" pattern="HH:mm"/>

    <span class="destiny-time"><spring:message code="user.trip.departing" arguments="${trip.from_city}"/></span>
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

    <a class="destiny-time map-trigger" target="iframe" href="https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${trip.departure_lat}, ${trip.departure_lon}&destination=${trip.arrival_lat}, ${trip.arrival_lon}" ><spring:message code="user.trip.map"/></a>
    <button class="destiny-unreserve-button" onclick="deleteTrip(${trip.id})"><spring:message code="user.trip.delete"/></button>
    <c:if test="${not empty trip.passengers}">
      <hr>
      <c:forEach items="${trip.passengers}" var="passenger">
        <a href="<c:url value='/user/${passenger.id}' />">
          <div class="driver">
            <div class="driver-item-data">
              <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${passenger.first_name} ${passenger.last_name}" alt="">
              <div class="driver-info">
                <span class="driver-name">${passenger.first_name} ${passenger.last_name}</span>
                <span>${passenger.phone_number}</span>
              </div>
            </div>
            <a onclick="kickout(${passenger.id}, ${trip.id})" type="button" class="kick-hitchhiker" href="#">
              <img src="<c:url value='/static/images/delete.png' />" height="20px" width="20px" alt="">
            </a>
          </div>
        </a>
      </c:forEach>
    </c:if>
  </div>
</li>
