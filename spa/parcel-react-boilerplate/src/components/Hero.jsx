import React from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { differenceInDays } from "date-fns";
import styled from "styled-components";

const NullDropzone = styled.div``;

import { updateCoverImageById, updateProfileImageById } from "../services/User";

import AbstractDropzone from "./AbstractDropzone";

import profileCss from "../styles/profile";

const Hero = ({ user, hero_message, editable }) => {
  const { t } = useTranslation();
  const editableClass = editable ? "editable" : "";
  const defaultProfileImageSrc = `https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${
    user.first_name
  } ${user.last_name}`;
  const daysRegistered = differenceInDays(new Date(), new Date(user.created));

  const coverImage = user.cover && {
    backgroundImage: `url(${user.cover})`,
  };

  const Dropzone = editable ? AbstractDropzone : NullDropzone;

  const onImageLoaded = callback => (imageURL, imageRAW, file) => {
    const image = new Image();
    image.src = imageURL;
    image.onload = () =>
      callback(user.id, {
        URL: imageURL,
        RAW: imageRAW,
        element: image,
        file,
      });
  };

  return (
    <React.Fragment>
      <style jsx>{profileCss}</style>

      <Dropzone onLoad={onImageLoaded(updateCoverImageById)}>
        <div
          className={`profile-hero-container ${editableClass}`}
          style={coverImage}
        >
          <div className="profile-hero-alignment">
            <div onClick={e => e.stopPropagation()}>
              <Dropzone
                onLoad={onImageLoaded(updateProfileImageById)}
                extra={{ noDragEventsBubbling: true }}
              >
                <img
                  width="100"
                  height="100"
                  className="profile-image"
                  src={user.image || defaultProfileImageSrc}
                  alt=""
                />
              </Dropzone>
            </div>

            <div className="profile-user-container">
              <span className="profile-user-name">{user.first_name}</span>
              {user.rating >= 0 && (
                <span className={`stars-${user.rating}-white`} />
              )}
              <span className="profile-user-created">
                {daysRegistered
                  ? t("common.hero.title", { "0": daysRegistered })
                  : t("common.hero.first_day")}
              </span>
            </div>
          </div>
        </div>
      </Dropzone>
      <div className="profile-hero-catchphrase">
        <span>{hero_message}</span>
      </div>
      <div className="profile-hero-border" />
    </React.Fragment>
  );
};

export default Hero;
