import React from "react";
import styled, { css } from "styled-components";

export const Grid = styled.div`
  margin-right: auto;
  margin-left: auto;
`;

export const Row = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: ${p => (p.nowrap ? "nowrap" : "wrap")};
  ${p =>
    p.middle &&
    css`
      align-items: center;
    `};
  ${p =>
    p.space &&
    css`
      justify-content: space- ${p.space};
    `};
`;

export const Col = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: ${p => p.grow || 0};
  flex-shrink: ${p => p.shrink || 0};
  ${p =>
    p.center &&
    css`
      align-items: center;
    `};
  justify-items: stretch;
`;
