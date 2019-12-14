const { default: compose } = require('../../src/utils/compose')
const assert = require('assert')

const assertFunctionEqual = (f, g, inputs) =>
  inputs.forEach(input => assert.equal(f(input), g(input)))

describe('compose', function () {
  it('returns identity on no args', function () {
    const result = compose()
    const expectedFunc = x => x

    const inputs = [1, 2, 3, 20]

    inputs.forEach(input => assert.equal(result(input), expectedFunc(input)))
  })

  it('returns composition with 2 arguments', function () {
    const add2 = x => x + 2
    const times5 = x => x * 5

    const expectedFunc = x => x * 5 + 2
    const result = compose(add2, times5)

    const inputs = [0, 5, 6, 20, 144]

    inputs.forEach(input => assert.equal(result(input), expectedFunc(input)))
  })

  it('returns composition with 10 arguments', function () {
    const add2 = x => x + 2
    const funcs = Array.from({ length: 10 }, _ => add2)
    const result = compose(...funcs)

    const expectedFunc = x => x + 20

    const inputs = [0, 5, 6, 20, 144]

    inputs.forEach(input => assert.equal(result(input), expectedFunc(input)))
  })
})
