import axios from 'axios'

const auth = () => localStorage.getItem('token')

const instance = axios.create({
  baseURL: 'http://c2af0ed0.ngrok.io/api',
  transformRequest: [
    function (data, headers) {
      const token = auth()

      if (token) {
        headers['Authorization'] = token
      } else {
        delete headers.Authorization
      }

      return data
    }
  ]
})

export const getUser = userId =>
  instance.get(`/users/${userId}`).then(res => res.data)

// instance.interceptors.request.use(config => {

//   instance.defaults.headers.common['Authorization'] = token

//   console.log({ token })
//   return config
// })

export default instance
