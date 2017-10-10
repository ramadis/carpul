<div class="navbar orange-border">
  <div class="top-section flex align-center">
    <a href="<c:url value='/' />"><img src="<c:url value='/static/images/logo.png' />" alt=""></img></a>
    <div class="actions">
      <c:if test="${empty user}">
        <a href="<c:url value='/user' />" class="create-account bold m-r-10" >Create account</a>
      </c:if>
      <c:if test="${not empty user}">
        <a href="<c:url value='/logout' />" class="create-account bold m-r-10" >Logout</a>
      </c:if>
      <c:if test="${empty user}">
        <a href="<c:url value='/login' />" class="login-button" >Login</a>
      </c:if>
      <c:if test="${not empty user}">
        <a href="<c:url value='/user/${user.id}' />" class="login-button" >Profile</a>
      </c:if>
    </div>
  </div>
</div>
