import { NotificationManager } from 'react-notifications'
import { isNumber } from 'util'

export const query = params =>
  '?' +
    Object.entries(params)
      .filter(([_, val]) => typeof val === 'number' || val) // Keep `0` but filter null/undefined
      .map(([key, value]) => key + '=' + value)
      .join('&') || ''

export const requestCatch = error => {
  console.error(error)
  const title =
    (error.message && error.message.title) || 'Something bad happened'
  const subtitle =
    (error.message && error.message.subtitle) ||
    "Unfortunately, we don't know more at this time. Please try again later"

  NotificationManager.error(subtitle, title)
}
