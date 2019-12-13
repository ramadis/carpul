<jsp:useBean id="date" class="java.util.Date"/>
<jsp:setProperty name="date" property="time" value="${history.created.time}"/>


<li class="review-item-container">
  <fmt:formatDate value="${history.trip.etd}" var="fmtetd" pattern="dd/MM/yyyy"/>
  <fmt:formatDate value="${date}" var="fmtdate" pattern="dd/MM/yyyy HH:mm"/>

  <img width="50" height="50"  src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${history.related.first_name} ${history.related.last_name}" alt="">
  <div class="review-item-content history-content">
    <c:if test="${history.type eq 'RESERVE'}">
      <spring:message code="history.item.reserved" var="message"/>
      <spring:message code="history.item.all_set" var="validation_message"/>
      <span class="review-message"><spring:message code="history.item.message" arguments="${history.related.first_name},${message},${history.trip.to_city},${fmtetd},${validation_message}"/></span>
    </c:if>
    <c:if test="${history.type eq 'UNRESERVE'}">
      <spring:message code="history.item.unreserved" var="message"/>
      <spring:message code="history.item.bummer" var="validation_message"/>
      <span class="review-message"><spring:message code="history.item.message" arguments="${history.related.first_name},${message},${history.trip.to_city},${fmtetd},${validation_message}"/></span>
    </c:if>
    <c:if test="${history.type eq 'DELETE'}">
      <spring:message code="history.item.deleted" var="message"/>
      <spring:message code="history.item.just_deleted" var="validation_message"/>
      <span class="review-message"><spring:message code="history.item.deleted_message" arguments="${history.trip.to_city},${fmtetd},${validation_message}"/></span>
    </c:if>
    <c:if test="${history.type eq 'KICKED'}">
      <spring:message code="history.item.just_deleted" var="validation_message"/>
      <span class="review-message"><spring:message code="history.item.kicked_message" arguments="${history.trip.to_city},${fmtetd},${validation_message}"/></span>
    </c:if>
    <span class="review-meta"><spring:message code="history.item.happened" arguments="${fmtetd}"/></span>
  </div>
</li>
