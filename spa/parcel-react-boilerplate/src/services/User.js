import { GETwithAuth, POSTwithAuth, PUTwithAuth } from './Utils'
import store from '../state/store'

export const userid = () => {
  const state = store.getState()
  return state.user && state.user.id
}

export const getProfileById = async id => {
  const user = await GETwithAuth(`/users/${id}`).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })
  return user
}

export const getProfile = async () => {
  const user = await GETwithAuth('/users').then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })

  localStorage.setItem('user', JSON.stringify(user))
  return user
}

export const updateProfileById = async (id, profile) => {
  const user = await PUTwithAuth(`/users/${id}/profile`, profile).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })
  return user
}

export const updateCoverImageById = async (id, image) => {
  const user = await PUTwithAuth(`/users/${id}/cover`, image).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })
  return user
}

export const updateProfileImageById = async (id, image) => {
  const user = await PUTwithAuth(`/users/${id}/profile`, image).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })
  return user
}

export const signupUser = async profile => {
  const user = await POSTwithAuth('/users', profile).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })
  return user
}

export const updateProfile = async profile => {
  const updatedUser = await POSTwithAuth(
    `/users/${userid()}/profile`,
    profile
  ).then(res => {
    if (res.isRawResponse) {
      // TODO: Handle specific error messages
      return
    }
    return res
  })

  return updatedUser
}
