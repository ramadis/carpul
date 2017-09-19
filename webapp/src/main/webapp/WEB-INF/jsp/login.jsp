<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Carpul - Log in</title>
    <style>
      body, html {
        height: 100vh;
        margin: 0;
      }

      * {
        font-family: Helvetica;
        box-sizing: border-box;
      }
      body {
        background: #e36f4a;
        flex-direction: column;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .user-form {
        position: relative;
        padding: 40px 45px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-around;
        width: 375px;
        height: 425px;
        background: white;
        -webkit-box-shadow: 0px 0px 28px 0px rgba(0,0,0,0.63);
        -moz-box-shadow: 0px 0px 28px 0px rgba(0,0,0,0.63);
        box-shadow: 0px 0px 28px 0px rgba(0,0,0,0.63);
      }

      .login-button {
        margin-left: 10px;
        padding: 10px 15px;
        font-size: 14px;
        background: #6CD298;
        border: 0;
        border-radius: 3px;
        color: white;
      }

      .text-container {
        width: 100%;
        display: flex;
        justify-content: center;
        flex-direction: column;
        text-align: center;
      }

      .catchphrase {
        font-size: 32px;
        font-weight: 100;
        color: #82C0C2;
      }

      .catchphrase-description {
        margin-top: 5px;
        font-size: 14px;
        color: #a5a5a5;
      }

      .top-border {
        position: absolute;
        top: -10px;
        background: linear-gradient(to right, #77DE5F 0%, #61C8CA 100%);
        border-top-left-radius: 7px;
        border-top-right-radius: 7px;
        width: 100%;
        height: 10px;
      }

      .field-container {
        width: 100%;
        height: 120px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
      }

      .field-label {
        font-size: 13px;
        color: #8A8A8A;
      }

      .field {
        padding-left: 10px;
        width: 100%;
        height: 32px;
        margin-top: 5px;
        border: 1px solid darkgray;
        border-radius: 3px;
        background: #F9F9F9;
      }

      .actions {
        width: 100%;
        justify-content: flex-end;
        display: flex;
        align-items: center;
      }

      .create-account {
        color: #AEAEAE;
        font-size: 13px;
        text-decoration: none;
      }
    </style>
  </head>
  <body>

    <form:form method="post" modelAttribute="userForm" action="${loginUserURI}" class="user-form">
        <div class="top-border"></div>
        <div class="text-container">
          <span>carpul</span>
          <span class="catchphrase">Have we met yet?</span>
          <span class="catchphrase-description">Create an account or login</span>
          <span class="catchphrase-description"> to start travelling to amazing places</span>
        </div>
          <div class="field-container">
            <spring:bind path="username">
                <label class="field-label" for="username">Email</label>
                <input class="field" name="username" path="username" type="text" />
            </spring:bind>
            <spring:bind path="password">
              <label class="field-label" for="password">Password</label>
              <input class="field" path="password" type="text" name="password"/>
            </spring:bind>
          </div>
          <div class="actions">
            <a href="${registerUserURI}" class="create-account">Create account</a>
            <button type="submit" class="login-button">Login</button>
          </div>
    </form:form>
  </body>
</html>
