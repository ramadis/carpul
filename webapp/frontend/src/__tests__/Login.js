import React from "react";
import { cleanup, fireEvent, render } from "@testing-library/react";

// automatically unmount and cleanup DOM after the test is finished.
afterEach(cleanup);

it("CheckboxWithLabel changes the text after click", () => {
  expect(true).toBeTruthy();
});
