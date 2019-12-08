import css from "styled-jsx/css";

export default css`
  .my-masonry-grid {
    display: flex;
    margin-left: -30px; /* gutter size offset */
    width: auto;
  }

  .my-masonry-grid_column {
    padding-left: 30px; /* gutter size */
    background-clip: padding-box;
  }

  .my-masonry-grid_column > div {
    /* change div to reference your elements you put in <Masonry> */
    border-radius: 5px;
    width: 300px;
    text-align: center;
    color: white;
    background-size: cover;
    background-color: rgba(0, 0, 0, 0.2);
    background-blend-mode: darken;
    background-position: center;
    width: 300px;
    margin-bottom: 30px;
  }
`;
