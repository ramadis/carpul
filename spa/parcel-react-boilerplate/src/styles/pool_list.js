import css from "styled-jsx/css";
export default css`
  :global(#app),
  body {
    background: #f4f4f4;
  }

  .pool-item {
    margin-top: 20px;
  }

  .destination {
    height: 45px;
    border: 1px solid darkgray;
    border-right: 0;
    border-left: 0;
    padding: 0 30px;
  }

  .destination-container {
    margin-right: 15px;
  }

  .user-info {
    margin: 0 50px;
  }

  .user-image {
    width: 85px;
    height: 85px;
    border-radius: 100%;
  }

  .map-container {
    height: 200px;
  }

  .pool-info {
    background: red;
    border-radius: 5px;
    overflow: hidden;
  }

  .small-pool-info {
    padding: 20px;
    width: 300px;
    text-align: center;
    color: white;
    background: url("../images/cabin.jpg");
    background-size: cover;
    background-color: rgba(0, 0, 0, 0.2);
    background-blend-mode: darken;
    background-position: center;
  }

  .small-pool-info .header-container {
    margin-bottom: 10px;
  }

  .small-pool-info .header {
    font-size: 30px;
  }

  .small-pool-info .CTA {
    margin-top: 10px;
  }

  .price-container {
    height: 50px;
    border-bottom: 1px solid lightgray;
  }

  .pool-features {
    height: 50px;
  }

  .price-container,
  .pool-features {
    padding: 0 20px;
  }

  .list-subtitle {
    color: #868686;
    text-align: center;
    display: block;
    margin: 20px 0;
    font-size: 20px;
  }

  .seats {
    display: block;
    align-items: center;
    display: flex;
    width: 120px;
    justify-content: space-around;
  }

  .seats-icon {
    height: 20px;
  }
`;
