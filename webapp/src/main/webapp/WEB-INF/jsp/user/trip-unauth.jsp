<li class="destiny-item trip-item" data-id="${trip.id}">
  <div class="inline-block no-margin">
    <c:if test="${trip.occupied_seats eq 0}">
      <span class="destiny-cost">Earning <span class="bold" style="display: inline;">nothing yet</span></span>
    </c:if>
    <c:if test="${trip.occupied_seats ne 0}">
      <span class="destiny-cost">Earning <span class="bold" style="display: inline;">$${trip.cost}</span></span>
    </c:if>
    <span class="destiny-name">${trip.to_city}</span>
    <span class="destiny-time">Depart from ${trip.from_city} on {Sunday} at ${trip.etd}</span>
    <span class="destiny-time">Arrive on ${trip.to_city} on {Sunday} at ${trip.eta}</span>
    <c:if test="${not empty trip.passengers}">
      <hr>
      <c:forEach items="${trip.passengers}" var="passenger">
        <c:if test="${empty passenger}">
        </c:if>
        <div class="driver">
          <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${passenger.first_name} ${passenger.last_name}" alt="">
          <div class="driver-info">
            <span class="driver-name">${passenger.first_name} ${passenger.last_name}</span>
            <span>${passenger.phone_number}</span>
          </div>
        </div>
      </c:forEach>
    </c:if>
  </div>
</li>
