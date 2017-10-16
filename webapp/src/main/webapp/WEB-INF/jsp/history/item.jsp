<jsp:useBean id="date" class="java.util.Date"/>
<jsp:setProperty name="date" property="time" value="${history.created.time}"/>


<li class="review-item-container">
  <img width="50" height="50"  src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${history.related.first_name} ${history.related.last_name}" alt="">
  <div class="review-item-content history-content">
    <c:if test="${history.type eq 'RESERVE'}">
        <c:set var="message" value="reserved"/>
        <c:set var="validation_message" value="All set for him!"/>
    </c:if>
    <c:if test="${history.type eq 'UNRESERVE'}">
        <c:set var="message" value="unreserved"/>
        <c:set var="validation_message" value="Bummer :("/>
    </c:if>
    <span class="review-message">${history.related.first_name} just ${message} your trip to ${history.trip.to_city} on ${history.trip.etd}. ${validation_message} </span>
    <span class="review-meta">Happened on <fmt:formatDate value="${date}" pattern="dd/MM/yyyy HH:mm"/></span>

  </div>
</li>
