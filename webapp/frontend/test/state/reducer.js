const assert = require('assert')
var jsdom = require('mocha-jsdom')

const nonExistentAction = { type: 'nonExistentAction' }

describe('reducer', function () {
  jsdom({ url: 'http://localhost' })

  it('should return initialState on undefined', function () {
    const {
      default: reducer,
      initialState
    } = require('../../src/state/reducer')

    const receivedState = reducer(undefined, nonExistentAction)

    assert.equal(receivedState, initialState)
  })
})
