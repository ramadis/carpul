import React, { useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { useTranslation } from "react-i18next";
import styled from "styled-components";

const getColor = props => {
  if (props.isDragAccept) {
    return "#6cd298";
  }
  if (props.isDragReject) {
    return "#ff1744";
  }
  if (props.isDragActive) {
    return "#2196f3";
  }
  return "#eeeeee";
};

const Container = styled.div`
  border-color: ${props => getColor(props)};
`;

export default ({ onLoad, children, extra = {} }) => {
  const { t, i18n } = useTranslation();
  const MAX_SIZE = 5 * 1024 ** 2;
  const SUPPORTED_FILES = ["image/png", "image/jpg", "image/gif", "image/jpeg"];

  const onDrop = useCallback(acceptedFiles => {
    const readAsURL = new Promise((res, rej) => {
      var reader = new FileReader();
      reader.onerror = rej;
      reader.onload = () => res(reader.result);
      reader.readAsDataURL(acceptedFiles[0]);
    });
    const readAsBytes = new Promise((res, rej) => {
      var reader = new FileReader();
      reader.onerror = rej;
      reader.onload = () => res(reader.result);
      reader.readAsArrayBuffer(acceptedFiles[0]);
    });
    const readers = Promise.all([readAsURL, readAsBytes, acceptedFiles[0]]);
    readers.then(results => onLoad(...results));
  }, []);

  console.log({ ...extra });

  const {
    getRootProps,
    getInputProps,
    isDragActive,
    isDragAccept,
    isDragReject,
  } = useDropzone({
    onDrop,
    maxSize: MAX_SIZE,
    accept: SUPPORTED_FILES,
    multiple: false,
    ...extra,
  });

  const inputProps = { ...getInputProps() };
  return (
    <Container {...getRootProps({ isDragActive, isDragAccept, isDragReject })}>
      <input {...inputProps} />
      {children}
    </Container>
  );
};
