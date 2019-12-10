import React from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { differenceInDays } from "date-fns";

import { updateCoverImageById, updateProfileImageById } from "../services/User";

import AbstractDropzone from "./AbstractDropzone";

const Hero = ({ user, hero_message, editable }) => {
  const { t } = useTranslation();
  const editableClass = editable ? "editable" : "";
  const defaultProfileImageSrc = `https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${
    user.first_name
  } ${user.last_name}`;
  const daysRegistered = differenceInDays(new Date(), new Date(user.created));

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
      <AbstractDropzone onLoad={onImageLoaded(updateCoverImageById)}>
        <div className={`profile-hero-container ${editableClass}`}>
          <div className="profile-hero-alignment">
            <AbstractDropzone
              onLoad={onImageLoaded(updateProfileImageById)}
              extra={{ noDragEventsBubbling: true }}
            >
              <img
                width="100"
                height="100"
                src={user.image || defaultProfileImageSrc}
                alt=""
              />
            </AbstractDropzone>
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
      </AbstractDropzone>
      <div className="profile-hero-catchphrase">
        <span>{hero_message}</span>
      </div>
      <div className="profile-hero-border" />
    </React.Fragment>
  );
};

export default Hero;
