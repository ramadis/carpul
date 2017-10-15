<li class="review-item-container">
  <img width="75" height="75"  src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${review.owner.first_name} ${review.owner.last_name}" alt="">
  <div class="review-item-content">
    <span class="stars-${review.stars}"></span>
    <span class="review-message">${review.message}</span>
    <span class="review-meta">From <span class="bold inline">${review.trip.from_city}</span> to <span class="bold inline">${review.trip.to_city}</span></span>
  </div>
</li>
