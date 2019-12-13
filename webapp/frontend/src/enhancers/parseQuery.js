import React from 'react'
import queryString from 'query-string'

const parseQuery = Comp => {
  return props => {
    const query = queryString.parse(props.location.search)
    return <Comp query={query} {...props} />
  }
}

export default parseQuery
