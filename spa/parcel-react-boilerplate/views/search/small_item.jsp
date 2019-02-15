<c:set var="url" value=""/>
<div class="pool-item flex-center">
  <div class="pool-info small-pool-info">
    <div class="header-container">
      <span class="bold header"> ${trip.from_city} <spring:message code="home.index.small_to"/> ${trip.to_city}</span>
    </div>

    <div >
      <spring:message code="home.index.leave_on"/>
      <span class="bold"> <fmt:formatDate value="${trip.etd}" pattern="dd/MM/yyyy"/></span>
      <spring:message code="search.item.at"/>
      <span class="bold"> <fmt:formatDate value="${trip.etd}" pattern="HH:mm"/></span>
    </div>

    <div class="">
      <spring:message code="home.index.just_price"/> <span class="bold">$${trip.cost}</span>
    </div>

    <c:if test="${not trip.reserved}">
      <form:form class="inline-block CTA" method="post" action="${url}trip/${trip.id}/reserve">
        <button class="login-button"><spring:message code="search.item.reserve"/></button>
      </form:form>
    </c:if>
    <c:if test="${trip.reserved}">
      <form:form class="inline-block CTA" method="post" action="${url}trip/${trip.id}/unreserve">
        <button class="login-button main-color"><spring:message code="search.item.unreserve"/></button>
      </form:form>
    </c:if>
  </div>
</div>
