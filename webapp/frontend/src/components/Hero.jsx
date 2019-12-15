import React, { Fragment, useState } from "react";
import { useTranslation } from "react-i18next";
import { connect } from "react-redux";
import { differenceInDays } from "date-fns";
import styled from "styled-components";
import { NotificationManager } from "react-notifications";
import MDSpinner from "react-md-spinner";

import { getProfileById } from "../services/User";
import { updateCoverImageById, updateProfileImageById } from "../services/User";

import { requestCatch } from "../utils/fetch";

import AbstractDropzone from "./AbstractDropzone";

import profileCss from "../styles/profile";

const NullDropzone = styled.div``;

const ProfileImage = ({ src }) => {
  return (
    <Fragment>
      <style jsx>{profileCss}</style>

      <img
        width="100"
        height="100"
        className="profile-image"
        src={`${src}`}
        alt=""
      />
    </Fragment>
  );
};

const Hero = ({ user, hero_message, editable, onUserUpdate }) => {
  const { t } = useTranslation();
  const [coverLoading, setCoverLoading] = useState(false);
  const [profileLoading, setProfileLoading] = useState(false);

  const defaultProfileImageSrc = `https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${
    user.first_name
  } ${user.last_name}`;
  const daysRegistered = differenceInDays(new Date(), new Date(user.created));
  const editableClass = editable ? "editable" : "";
  const Dropzone = editable ? AbstractDropzone : NullDropzone;

  const setterMap = new Map([
    [updateCoverImageById, setCoverLoading],
    [updateProfileImageById, setProfileLoading],
  ]);

  const coverImage = user.cover && {
    backgroundImage: `url(${user.cover})`,
  };

  const coverImageLoading = {
    background: `grey`,
  };

  const onImageLoaded = callback => (imageURL, imageRAW, file) => {
    if (!file) return;
    const image = new Image();
    image.src = imageURL;
    image.onload = async () => {
      setterMap.get(callback)(true);
      await callback(user.id, {
        URL: imageURL,
        RAW: imageRAW,
        element: image,
        file,
      }).catch(requestCatch);
      await getProfileById(user.id)
        .then(onUserUpdate)
        .catch(requestCatch);
      NotificationManager.success(
        "Perfect to let other adventurers to get to know you",
        "Image updated successfully"
      );
      setterMap.get(callback)(false);
    };
  };

  return (
    <React.Fragment>
      <Dropzone onLoad={onImageLoaded(updateCoverImageById)}>
        <div
          className={`profile-hero-container ${editableClass}`}
          style={coverLoading ? coverImageLoading : coverImage}
        >
          <div className="profile-hero-alignment">
            <div onClick={e => e.stopPropagation()}>
              <Dropzone
                onLoad={onImageLoaded(updateProfileImageById)}
                extra={{ noDragEventsBubbling: true }}
              >
                {profileLoading ? (
                  <MDSpinner size={36} />
                ) : (
                  <ProfileImage src={user.image || defaultProfileImageSrc} />
                )}
              </Dropzone>
            </div>

            <div className="profile-user-container">
              <span className="profile-user-name">
                {coverLoading && <MDSpinner size={16} />} {user.first_name}
              </span>
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
