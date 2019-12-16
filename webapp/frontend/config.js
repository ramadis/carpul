const config = {
  local: {
    apiServer: 'http://jsonplaceholder.typicode.com'
  },
  prod: {
    apiServer: 'https://jsonplaceholder.typicode.com'
  }
}

const deploy = preval`
const deploy = process.env.DEPLOY || "local";
module.exports = deploy;
`

export default config[deploy]
