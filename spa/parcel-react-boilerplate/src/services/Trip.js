import { GETwithAuth, DELETEwithAuth, POSTwithAuth } from "./Utils";

export const createTrip = async trip => {
  const trips = await POSTwithAuth(`/trips`, trip).then(res => {
    if (res.isRawResponse) {
      const errors = {
        400: {
          title: "The trip content has some problems",
          subtitle: "Try fixing the errors and submitting it again.",
        },
        409: {
          title: "You can't create a trip if you have other plans",
          subtitle:
            "It seems like you have another trip or a reservation overlapping with this time range.",
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
  return trips;
};

export const getTripById = async id => {
  const trips = await GETwithAuth(`/trips/${id}`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return trips;
};

export const getTripsByUser = async id => {
  const trips = await GETwithAuth(`/users/${id}/trips`).then(res => {
    if (res.isRawResponse) {
      const errors = {
        404: {
          title: "We can't find the user",
          subtitle: "You sure you are trying to access an existing user?",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "getTripsByUser",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return trips;
};

export const cancelTrip = async id => {
  const trip = await DELETEwithAuth(`/trips/${id}`).then(res => {
    if (res.isRawResponse) {
      const errors = {
        403: {
          title: "You can't cancel the trip",
          subtitle: "Only the driver of this trip can, sorry",
        },
        404: {
          title: "We can't find the trip",
          subtitle: "You sure you are trying to delete an existing trip?",
        },
        default: {
          title: "Something went wrong",
          subtitle: "And we don't know what it is, sorry :(.",
        },
      };

      throw {
        origin: "cancel-trip",
        message: errors[res.status] || errors.default,
        code: res.status,
      };
    }
    return res;
  });
  return trip;
};
