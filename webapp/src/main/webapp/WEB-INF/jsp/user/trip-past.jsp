<li class="destiny-item past-item" data-id="${reservation.id}">
  <div class="inline-block no-margin">
    <span class="destiny-cost">Review your trip to</span>
    <span class="destiny-name">${reservation.to_city}</span>
    <a href="<c:url value='../review/${reservation.id}' />" class="login-button review-button" name="button">Review</a>
  </div>
</li>
