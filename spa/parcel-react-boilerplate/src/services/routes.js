export const routes = {
  search: ({ from, to, date }) =>
    `/search?from=${from.city}&to=${to}&date=${date}`
}
