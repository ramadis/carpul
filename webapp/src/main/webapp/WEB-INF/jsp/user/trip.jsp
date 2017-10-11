<li class="destiny-item trip-item" data-id="${trip.id}">
  <form:form class="inline-block no-margin" method="post" action="../delete/${trip.id}">
    <span class="destiny-cost">Earning <span class="bold" style="display: inline;">$${trip.cost}</span></span>
    <span class="destiny-name">${trip.to_city}</span>
    <span class="destiny-time">Depart from ${trip.from_city} on {Sunday} at ${trip.etd}</span>
    <span class="destiny-time">Arrive on ${trip.to_city} on {Sunday} at ${trip.eta}</span>
    <button class="destiny-unreserve-button">Delete trip</button>
    <hr>
    <div class="driver">
      <img width="50" height="50" src="https://ui-avatars.com/api/?rounded=true&size=150&background=e36f4a&color=fff&name=${reservation.driver.first_name} ${reservation.driver.last_name}" alt="">
      <div class="driver-info">
        <span class="driver-name">${reservation.driver.first_name} ${reservation.driver.last_name}</span>
        <span>${reservation.driver.phone_number}</span>
      </div>
    </div>
  </form:form>
</li>
