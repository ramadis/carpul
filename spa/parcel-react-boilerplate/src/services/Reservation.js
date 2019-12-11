import { GETwithAuth, PUTwithAuth, DELETEwithAuth } from "./Utils";

export const getReservationsByUser = async id => {
  const reservations = await GETwithAuth(`/users/${id}/reservations`).then(
    res => {
      if (res.isRawResponse) {
        const errors = {
          404: {
            title: "We can't find the user",
            subtitle: "You sure you are trying to access the correct one?",
          },
          default: {
            title: "Something went wrong",
            subtitle: "And we don't know what it is, sorry :(.",
          },
        };

        throw {
          message: errors[res.status] || errors.default,
          code: res.status,
        };
      }
      return res;
    }
  );
  return reservations;
};

export const reserveByTrip = async id => {
  const reservation = await PUTwithAuth(`/trips/${id}/reservation`).then(
    res => {
      if (res.isRawResponse) {
        // TODO: Handle specific error messages
        return;
      }
      return res;
    }
  );
  return reservation;
};

export const unreserveByTrip = async id => {
  const reservation = await DELETEwithAuth(`/trips/${id}/reservation`).then(
    res => {
      if (res.isRawResponse) {
        // TODO: Handle specific error messages
        return;
      }
      return res;
    }
  );
  return reservation;
};

export const cancelReservation = async (passenger, trip) => {
  const reservation = await DELETEwithAuth(
    `/trips/${trip}/passengers/${passenger}`
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return reservation;
};
