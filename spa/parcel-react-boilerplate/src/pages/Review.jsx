import React, { useState, useEffect, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import MDSpinner from "react-md-spinner";
import { useParams } from "react-router-dom";
import Rating from "react-rating";
import styled from "styled-components";

import { getTripById } from "../services/Trip.js";
import { reviewTrip, addReviewImage } from "../services/Review.js";

import Hero from "../components/Hero";

import profileHeroCss from "../styles/profile_hero";
import poolListCss from "../styles/pool_list";
import profileCss from "../styles/profile";
import reviewItemCss from "../styles/review_item";

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
  flex: 1;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  border-width: 2px;
  border-radius: 2px;
  border-color: ${props => getColor(props)};
  border-style: dashed;
  background-color: #fafafa;
  color: #bdbdbd;
  outline: none;
  transition: border 0.24s ease-in-out;
`;

const Dropzone = ({ onLoad }) => {
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
  });

  return (
    <Container {...getRootProps({ isDragActive, isDragAccept, isDragReject })}>
      <input {...getInputProps()} />
      <p>{t("review.add.dropzone")}</p>
    </Container>
  );
};

const Review = ({ user }) => {
  const { t, i18n } = useTranslation();
  const { id: tripId } = useParams();

  const [image, setImage] = useState();
  const [trip, setTrip] = useState();
  const [stars, setStars] = useState(0);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const onImageLoaded = (imageURL, imageRAW, file) => {
    const image = new Image();
    image.src = imageURL;
    image.onload = () =>
      setImage({
        URL: imageURL,
        RAW: imageRAW,
        element: image,
        file,
      });
  };

  const review = async () => {
    try {
      setLoading(true);
      const review = await reviewTrip(trip.id, { stars, message });
      if (image) await addReviewImage(review.id, image);
      setLoading(false);
    } catch (e) {
      setLoading(false);
      console.error(e);
    }
  };

  useEffect(() => {
    getTripById(tripId).then(setTrip);
  }, []);

  window.document.title = t("review.add.page_title");

  const isLoading = !user || !trip;

  if (isLoading) {
    return (
      <React.Fragment>
        <style jsx>{poolListCss}</style>
        <style jsx>{profileCss}</style>
        <style jsx>{reviewItemCss}</style>
        <style jsx>{profileHeroCss}</style>
        <div className="flex-center spinner-class">
          <MDSpinner size={36} />
        </div>
      </React.Fragment>
    );
  }

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      <Hero hero_message={t("review.add.hero")} user={user} />

      <div className="profile-form-container flex-center">
        <div className="new-trip-form" action="../review/${trip.id}">
          <h3>{t("review.add.title")}</h3>
          <h2>
            {t("review.add.subtitle", {
              "0": user.first_name,
              "1": trip.driver.first_name,
              "2": trip.from_city,
              "3": trip.to_city,
            })}
          </h2>

          <div className="field-container">
            <label path="message" className="field-label" htmlFor="message">
              {t("review.add.message")}
            </label>
            <Rating initialRating={stars} onChange={setStars} />
            {image ? (
              <img
                src={image.URL}
                height={100}
                width={(100 * image.element.width) / image.element.height}
              />
            ) : (
              <Dropzone onLoad={onImageLoaded} />
            )}
            <textarea
              value={message}
              onChange={e => setMessage(e.target.value)}
              className="field"
              placeholder={t("review.add.placeholder")}
              required={true}
              multiline="true"
              name="message"
              path="message"
              type="text"
            />
          </div>

          <div className="actions">
            <button type="submit" onClick={review} className="login-button">
              {loading ? <MDSpinner size={24} /> : t("review.add.submit")}
            </button>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};

export default connect(state => ({ user: state.user }))(Review);
