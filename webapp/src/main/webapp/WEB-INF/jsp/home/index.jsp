<%-- Comments so as to know from where the variables come from --%>
<%--@elvariable id="user" type="ar.edu.itba.paw.models.User"--%>
<%--@elvariable id="greeting" type="ar.edu.itba.paw.webapp.controllers.HelloWorldController"--%>

<%--
    This was the one Sotuyo gave us. The second one is the recommended one.
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Carpul | Travel cheap long trips</title>
  <link href="<c:url value='/static/css/css.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/pool_list.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/home.css' />" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/static/css/datetime.component.css' />" rel="stylesheet" type="text/css" />

  <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
  <script src="<c:url value='/static/js/datetime.component.js' />" charset="utf-8"></script>
</head>
<body>
  <%@ include file="/WEB-INF/jsp/common/navbar.jsp" %>

  <div class="home-hero-container flex-center">
    <div class="home-content-container">
      <div class="titles">
        <h1 class="title">Travel cheap to amazing places</h1>
        <h1 class="title">by joining friendly adventurers.</h1>
      </div>

      <h2 class="subtitle">Are you ready? Where next?</h2>

      <form:form method="post" modelAttribute="searchForm" action="search">
        <div class="searchbar">
          <spring:bind path="from">
            <label class="searchbar-label" for="from">From</label>
            <input class="searchbar-input" placeholder="Origin" path="from" type="text" name="from" value="" tabindex="0"/>
          </spring:bind>

          <spring:bind path="to">
            <label class="searchbar-label" for="to">To</label>
            <input class="searchbar-input" placeholder="Destination" path="to" type="text" name="to" value="" tabindex="0"/>
          </spring:bind>

          <spring:bind path="when">
            <label class="searchbar-label" for="when">On</label>
            <input class="searchbar-input" id="when" placeholder="Time range" type="text" value="" tabindex="0"/>
            <form:input class="searchbar-input hide" path="when" type="text" name="when" value="" tabindex="0"/>
          </spring:bind>

          <button type="submit" class="login-button searchbar-button" name="button">Search</button>
        </div>
      </form:form>
    </div>
  </div>

  <script src="<c:url value='/static/js/search_time.js' />" charset="utf-8"></script>

</body>
</html>
