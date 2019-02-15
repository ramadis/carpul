<li class="review-item-container">
  <img width="75" height="75"  src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${review.owner.first_name} ${review.owner.last_name}" alt="">
  <div class="review-item-content">
    <c:if test="${review.stars eq 0}">
      <span class="review-meta"><spring:message code="review.item.no_stars"/></span>
    </c:if>
    <c:if test="${review.stars ne 0}">
      <span class="stars-${review.stars}"></span>
    </c:if>
    <span class="review-message">${review.message}</span>
    <span class="review-meta"><spring:message code="review.item.from"/> <span class="bold inline">${review.trip.from_city}</span> <spring:message code="review.item.to"/> <span class="bold inline">${review.trip.to_city}</span></span>
  </div>
</li>
