import css from "styled-jsx/css";

export default css`
  .review-item-container {
    display: flex;
    margin-bottom: 20px;
  }

  .review-item-container img {
    margin-right: 10px;
  }

  .review-meta {
    font-size: 12px;
    color: #adadad;
  }

  .review-meta.review-trip span {
    display: inline !important;
    margin: 0 5px;
  }

  .history-content {
    max-width: 15vw;
    line-height: 20px;
  }

  .review-message {
    max-width: 360px;
    word-wrap: break-word;
    color: #5a5a5a;
  }

  .review-item-container .inline {
    display: inline;
  }

  .stars-1 {
    background: url("../images/star.png");
    background-repeat: no-repeat;
    background-position: 0 top;
    height: 12px;
    width: 100px;
    margin-bottom: 5px;
  }

  .stars-2 {
    background: url("../images/star.png"), url("../images/star.png");
    background-repeat: no-repeat, no-repeat;
    background-position: 0 top, 12px top;
    height: 12px;
    width: 100px;
    margin-bottom: 5px;
  }

  .stars-3 {
    background: url("../images/star.png"), url("../images/star.png"),
      url("../images/star.png");
    background-repeat: no-repeat, no-repeat, no-repeat;
    background-position: 0 top, 12px top, 24px top;
    height: 12px;
    width: 100px;
    margin-bottom: 5px;
  }

  .stars-4 {
    background: url("../images/star.png"), url("../images/star.png"),
      url("../images/star.png"), url("../images/star.png");
    background-repeat: no-repeat, no-repeat, no-repeat, no-repeat;
    background-position: 0 top, 12px top, 24px top, 36px top;
    height: 12px;
    width: 100px;
    margin-bottom: 5px;
  }

  .stars-5 {
    background: url("../images/star.png"), url("../images/star.png"),
      url("../images/star.png"), url("../images/star.png"),
      url("../images/star.png");
    background-repeat: no-repeat, no-repeat, no-repeat, no-repeat, no-repeat;
    background-position: 0 top, 12px top, 24px top, 36px top, 48px top;
    height: 12px;
    width: 100px;
    margin-bottom: 5px;
  }

  .stars-1-white {
    background: url("../images/star-white.png");
    background-repeat: no-repeat;
    background-position: 0 top;
    height: 12px;
    width: 100px;
    margin: 5px 0;
  }

  .stars-2-white {
    background: url("../images/star-white.png"), url("../images/star-white.png");
    background-repeat: no-repeat, no-repeat;
    background-position: 0 top, 12px top;
    height: 12px;
    width: 100px;
    margin: 5px 0;
  }

  .stars-3-white {
    background: url("../images/star-white.png"), url("../images/star-white.png"),
      url("../images/star-white.png");
    background-repeat: no-repeat, no-repeat, no-repeat;
    background-position: 0 top, 12px top, 24px top;
    height: 12px;
    width: 100px;
    margin: 5px 0;
  }

  .stars-4-white {
    background: url("/images/star-white.png"), url("../images/star-white.png"),
      url("../images/star-white.png"), url("../images/star-white.png");
    background-repeat: no-repeat, no-repeat, no-repeat, no-repeat;
    background-position: 0 top, 12px top, 24px top, 36px top;
    height: 12px;
    width: 100px;
    margin: 5px 0;
  }

  .stars-5-white {
    background: url("../images/star-white.png"), url("../images/star-white.png"),
      url("../images/star-white.png"), url("../images/star-white.png"),
      url("../images/star-white.png");
    background-repeat: no-repeat, no-repeat, no-repeat, no-repeat, no-repeat;
    background-position: 0 top, 12px top, 24px top, 36px top, 48px top;
    height: 12px;
    width: 100px;
    margin: 5px 0;
  }
`;
