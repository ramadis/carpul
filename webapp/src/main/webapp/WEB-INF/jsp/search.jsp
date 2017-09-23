<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="./css.css">
    <link rel="stylesheet" href="./pool_list.css">
  </head>
  <body >
    <!-- soy un villero lol -->
    <div class="navbar">
      <div class="top-section flex align-center">
        <img src="./logo.png" alt=""></img>
        <div class="actions">
          <a href="" class="create-account bold m-r-10" >Create account</a>
          <button type="submit" class="login-button">Login</button>
        </div>
      </div>
      <div class="destination flex align-center">
        <div class="destination-container">
          <span class="bold m-r-5">From</span>
          <span class="clear">Buenos Aires</span>
        </div>
        <div class="destination-container">
          <span class="bold m-r-5">To</span>
          <span class="clear">Pinamar</span>
        </div>
        <div class="destination-container">
          <span class="bold m-r-5">On</span>
          <select name="on" class="clear">
            <option value="0">This weekend</option>
          </select>
        </div>
      </div>
    </div>

    <div class="list-container">
      <span class="list-subtitle">These are the options to go  to Pinamar this weekend</span>
      <!-- aca va el ng-repeat -->
      <div class="pool-item flex-center">
        <div class="user-info flex space-around align-center column h-150">
          <div class="user-image">

          </div>
          <div class="user-name">
            Mariana
          </div>
          <span class="user-rating">
            ★★★★★
          </span>
        </div>

        <div class="pool-info">
          <!-- aca iría el mapa arre -->
          <div class="map-container">
            <img src="https://puu.sh/xH5mj/28cb5c7eb2.png" style="width: 100%; height: 100%;"></img>
          </div>
          <div class="bg-white">
            <div class="price-container flex space-between align-center">
              <span class="clear gray sz-13">
                Leave from
                <span class="bold black"> Buenos Aires</span>
                at
                <span class="bold black"> 14:00</span>
                arrive on
                <span class="bold black"> Pinamar</span>
                at
                <span class="bold black">22:00</span>
              </span>
              <div>
                <span class="price gray">
                  <span class="bold black">$200</span>
                  /each
                </span>
                <button class="login-button">Reserve</button>
              </div>
            </div>

            <div class="pool-features flex space-between align-center">
              <div class="features-container">
                <span>icono1</span>
                <span>icono2</span>
                <span>icono3</span>
                <span>icono4</span>
              </div>
              <div class="seats-container">
                <span class="seats bold gray">
                  <img class="seats-icon" src="./seats.png"></img>
                  3 available
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
