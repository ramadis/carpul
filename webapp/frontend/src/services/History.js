import { GETwithAuth } from './Utils'
import { query } from '../utils/fetch'

export const getHistoryByUser = async (id, page) => {
  const res = await getHistoryByUserInternal(id, page)
  const nextPage = await getHistoryByUserInternal(id, page + 1)

  return { data: res, isLastPage: nextPage ? nextPage.length === 0 : true }
}

export const getHistoryByUserInternal = async (id, page) => {
  const history = await GETwithAuth(
    `/users/${id}/history${query({ page, per_page: 5 })}`
  ).then(res => {
    if (res.isRawResponse) {
      const errors = {
        404: {
          title: "We can't find the user",
          subtitle: 'You sure you are trying to access the correct one?'
        },
        default: {
          title: 'Something went wrong',
          subtitle: "And we don't know what it is, sorry :(."
        }
      }

      throw {
        message: errors[res.status] || errors.default,
        code: res.status
      }
    }
    return res
  })
  return history
}
