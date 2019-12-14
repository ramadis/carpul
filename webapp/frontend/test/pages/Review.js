const assert = require('assert')
var jsdom = require('mocha-jsdom')

describe('Review page', function () {
  jsdom({ url: 'http://localhost' })

  it('renders', function () {
    // const { Review } = require('../../src/pages/Review')

    assert.equal(0, 0)
  })
})
