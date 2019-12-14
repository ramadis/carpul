import { GETwithAuth, PUTwithAuth, DELETEwithAuth } from "./Utils";

export const getReservationsByUser = async id => {
  const reservations = await GETwithAuth(
    `/users/${id}/reservations?exclude_reviewed=false`
  ).then(res => {
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
  });
  return reservations;
};

export const reserveByTrip = async id => {
  const reservation = await POSTwithAuth(`/trips/${id}/reservation`).then(
    res => {
      if (res.isRawResponse) {
        const errors = {
          404: {
            title: "We can't find the trip to reserve",
            subtitle: "Are you sure this is a valid trip?",
          },
          409: {
            title: "You can't reserve this trip",
            subtitle:
              "It might be too old, full, you might have another time conflicting trip, or you might be the driver.",
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
  return reservation;
};

export const unreserveByTrip = async id => {
  const reservation = await DELETEwithAuth(`/trips/${id}/reservation`).then(
    res => {
      if (res.isRawResponse) {
        const errors = {
          404: {
            title: "We can't find the trip to reserve",
            subtitle: "Are you sure this is a valid trip?",
          },
          409: {
            title: "You can't unreserve this trip",
            subtitle: "IT ALREADY HAPPENED MY FRIEND.",
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
  return reservation;
};

export const cancelReservation = async (passenger, trip) => {
  const reservation = await DELETEwithAuth(
    `/trips/${trip}/passengers/${passenger}`
  ).then(res => {
    if (res.isRawResponse) {
      const errors = {
        403: {
          title: "You are not allowed to cancel passengers",
          subtitle: "Try logging in as the driver of the trip",
        },
        404: {
          title: "We can't find the user or the trip",
          subtitle:
            "Check you are trying to kick out a valid user from a valid trip.",
        },
        409: {
          title: "It's too late",
          subtitle:
            "You can't cancel a passenger after if the trip already ended.",
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
  });
  return reservation;
};
