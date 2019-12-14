import css from "styled-jsx/css";
import imgHomeHero from "../../images/home-hero.jpg";
export default css`
  .home-hero-container {
    position: relative;
    width: 100%;
    background-image: url("${imgHomeHero}");
    background-size: cover;
    height: 500px;
    background-color: #00c4ff;
    background-blend-mode: multiply;
  }

  input[readonly].searchbar-input {
    padding-left: 10px;
  }

  .home-content-container {
    width: 80%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .home-error-bar {
    border-radius: 3px;
    background: #e85252;
    padding: 10px 20px;
    margin: 0 auto;
    margin-top: 10px;
  }

  .home-error-bar.form-error {
    color: white;
  }

  .title {
    margin: 0;
    color: white;
    font-size: 50px;
  }

  .subtitle {
    margin: 0;
    margin-top: 20px;
    color: #d3f9ff;
    font-weight: 400;
  }

  .searchbar {
    background: white;
    margin-top: 50px;
    align-self: center;
    width: auto;
    border-radius: 5px;
    border: 2px solid #c5c5c5;
    padding: 0 0 0 10px;
  }

  .searchbar-button {
    border-radius: 0 5px 5px 0;
    padding: 15px 20px;
  }

  .searchbar-input {
    border: none;
    height: 46px;
    outline: none;
    padding-left: 10px;
    color: #4c4c4c;
    font-size: 16px;
  }

  .searchbar-label {
    font-weight: 700;
  }

  .trips-recommendations-container {
    width: 80%;
    margin: 0 auto;
    text-align: center;
  }

  .trips-recommendations-container h1 {
    color: #a0a0a0;
    margin-top: 40px;
    font-weight: 400;
    font-size: 26px;
  }

  .trip-recommendation-list {
    display: flex;
    flex-wrap: wrap;
    margin: 0 auto;
    width: 75%;
    justify-content: flex-start;
  }

  .trip-recommendation-item {
    color: white;
    cursor: pointer;
    width: 200px;
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    padding: 20px;
    background: #6cd298;
    border-radius: 5px;
    transition: 0.1s ease-in background;
  }

  .trip-recommendation-item:hover {
    background-color: #e36f4a;
  }
`;
