import { GETwithAuth, DELETEwithAuth, POSTwithAuth } from "./Utils";

export const createTripByUser = async (id, trip) => {
  const trips = await POSTwithAuth(`/users/${id}/trips`, trip).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
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
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return trips;
};

export const cancelTrip = async id => {
  const trip = await DELETEwithAuth(`/trips/${id}`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return;
    }
    return res;
  });
  return trip;
};
