<div class="navbar orange-border">
  <div class="top-section flex align-center">
    <a href="<c:url value='/' />"><img width="100" src="<c:url value='/static/images/logo.png' />" alt=""></img></a>
    <div class="actions">
      <c:if test="${empty user or empty user.id}">
        <a href="<c:url value='/user' />" class="create-account bold m-r-10" ><spring:message code="common.navbar.create"/></a>
      </c:if>
      <c:if test="${not empty user and not empty user.id}">
        <a href="<c:url value='/logout' />" class="create-account bold m-r-10" ><spring:message code="common.navbar.logout"/></a>
      </c:if>
      <c:if test="${empty user or empty user.id}">
        <a href="<c:url value='/login' />" class="login-button" ><spring:message code="common.navbar.login"/></a>
      </c:if>
      <c:if test="${not empty user and not empty user.id}">
        <a href="<c:url value='/user/${user.id}' />" class="login-button" ><spring:message code="common.navbar.profile"/></a>
      </c:if>
    </div>
  </div>
</div>
