<li class="destiny-item trip-item" data-id="${trip.id}">
  <form:form class="inline-block no-margin" method="post" action="../trip/${trip.id}/delete">
    <c:if test="${trip.occupied_seats eq 0}">
      <span class="destiny-cost"><spring:message code="user.trip.earning"/> <span class="bold" style="display: inline;"><spring:message code="user.trip.nil"/></span></span>
    </c:if>
    <c:if test="${trip.occupied_seats ne 0}">
      <span class="destiny-cost"><spring:message code="user.trip.earning"/> <span class="bold" style="display: inline;">$${trip.cost}</span></span>
    </c:if>
    <span class="destiny-name">${trip.to_city}</span>

    <fmt:formatDate value="${trip.etd}" var="fmtetddate" pattern="dd/MM/yyyy"/>
    <fmt:formatDate value="${trip.etd}" var="fmtetdtime" pattern="HH:mm"/>
    <fmt:formatDate value="${trip.eta}" var="fmtetadate" pattern="dd/MM/yyyy"/>
    <fmt:formatDate value="${trip.eta}" var="fmtetatime" pattern="HH:mm"/>

    <span class="destiny-time"><spring:message code="user.trip.depart" arguments="${trip.from_city},${fmtetddate},${fmtetdtime}"/></span>
    <span class="destiny-time"><spring:message code="user.trip.arrive" arguments="${trip.to_city},${fmtetadate},${fmtetatime}"/></span>
    <a class="destiny-time map-trigger" target="iframe" href="https://www.google.com/maps/embed/v1/directions?key=AIzaSyCNS1Xx_AGiNgyperC3ovLBiTdsMlwnuZU&origin=${trip.departure.latitude}, ${trip.departure.longitude}&destination=${trip.arrival.latitude}, ${trip.arrival.longitude}" ><spring:message code="user.trip.map"/></a>
    <button class="destiny-unreserve-button"><spring:message code="user.trip.delete"/></button>
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
