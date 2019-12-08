import React from "react";
import styled from "styled-components";

const ModalContainer = styled.div`
  width: 600px;
  border: 2px solid #ffc7c7;
  border-radius: 10px;
  background: #f4f4f4;
  padding: 50px;
`;

const Header = styled.h1`
  font-size: 25px;
  margin: 0;
  font-family: "Helvetica";
  margin-bottom: 10px;
  font-weight: 100;
`;

const Subtitle = styled.h2`
  font-size: 16px;
  font-weight: 100;
  margin: 0;
  color: rgba(0, 0, 0, 0.8);
`;

const Button = styled.button`
  margin-right: 10px;
  margin-left: 0;
  ${({ danger }) => danger && `background-color: #d24949;`}
`;
const ButtonContainer = styled.div`
  margin-top: 20px;
`;

export default (buttons = []) => props => {
  const { onClose, title, message } = props;
  console.log(props, buttons);
  return (
    <ModalContainer className="custom-ui">
      <Header>{title}</Header>
      <Subtitle>{message}</Subtitle>
      <ButtonContainer>
        {buttons.map(button => (
          <Button
            key={button.label}
            danger={button.danger}
            className="login-button"
            onClick={() => {
              button.onClick();
              onClose();
            }}
          >
            {button.label}
          </Button>
        ))}
      </ButtonContainer>
    </ModalContainer>
  );
};
