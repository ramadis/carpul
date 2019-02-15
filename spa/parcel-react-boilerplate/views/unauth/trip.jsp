<li class="destiny-item trip-item" data-id="${trip.id}">
  <fmt:formatDate value="${trip.etd}" var="fmtetddate" pattern="dd/MM/yyyy"/>
  <fmt:formatDate value="${trip.etd}" var="fmtetdtime" pattern="HH:mm"/>
  <fmt:formatDate value="${trip.eta}" var="fmtetadate" pattern="dd/MM/yyyy"/>
  <fmt:formatDate value="${trip.eta}" var="fmtetatime" pattern="HH:mm"/>

  <div class="inline-block no-margin">
    <span class="destiny-cost"><span class="bold" style="display: inline;">$${trip.cost}</span></span>
    <span class="destiny-name">${trip.to_city}</span>
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
    <c:if test="${not empty trip.passengers}">
      <hr>
      <c:forEach items="${trip.passengers}" var="passenger">
        <c:if test="${empty passenger}">
        </c:if>
        <div class="driver">
          <div class="driver-item-data">
            <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${passenger.first_name} ${passenger.last_name}" alt="">
            <div class="driver-info">
              <span class="driver-name">${passenger.first_name} ${passenger.last_name}</span>
              <span>${passenger.phone_number}</span>
            </div>
          </div>
        </div>
      </c:forEach>
    </c:if>
  </div>
</li>
