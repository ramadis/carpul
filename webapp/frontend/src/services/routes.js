export const routes = {
  search: ({ from, to, date = new Date() }) =>
    `/search?from=${from.city}&to=${to.city}&when=${date && date.getTime()}`,
};
