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
  NotificationManager.error(error.message.subtitle, error.message.title)
}
