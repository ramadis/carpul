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
<title>HelloWorldJSP</title>
</head>
<body>
    <h1>Registrar usuario</h1>
    <form:form method="post" modelAttribute="userForm" action="${registerUserURI}">
        <spring:bind path="username">
            <form:input path="username" type="text" />
            <form:errors path="username" />
        </spring:bind>

        <spring:bind path="password">
            <form:input path="password" type="text" />
            <form:errors path="password" />
        </spring:bind>

        <button type="submit">Registrar usuario</button>
    </form:form>
</body>
</html>