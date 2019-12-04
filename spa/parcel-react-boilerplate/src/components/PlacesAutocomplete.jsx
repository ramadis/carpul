import React, { Component, Fragment } from "react";
import { throttle } from "lodash";
import MDSpinner from "react-md-spinner";
import styled from "styled-components";

const ResultsContainer = styled.div`
  border: 1px solid darkgray;
  border-radius: 5px;
  margin-top: 10px;
  background: #f9f9f9;
  position: absolute;
  z-index: 100;
  width: 400px;
`;

const Container = styled.div`
  position: relative;
`;

const ResultList = styled.ul`
  list-style: none;
  padding: 15px;
  margin: 0;
`;

const Result = styled.li`
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background: #e36f49;
    color: white;
  }
`;

import { autocompletePlaces } from "../services/Places";

export default class PlacesAutocomplete extends Component {
  state = { results: [], disabled: false };

  componentDidUpdate(prevProps) {
    if (this.state.disabled) return;
    if (prevProps.value !== this.props.value && this.props.value) {
      this.search(this.props.value);
    }
  }

  search = throttle(
    () =>
      autocompletePlaces(this.props.value).then(results =>
        this.setState({ results })
      ),
    2000
  );

  handleBlur = () => {
    setTimeout(() => this.setState({ disabled: true }), 200);
  };

  handleFocus = () => {
    this.setState({ disabled: false });
  };

  select = result => {
    const { handleSelect } = this.props;
    this.setState({ disabled: true }, () => handleSelect(result));
  };

  render() {
    let { results, disabled } = this.state;
    const { children, style } = this.props;

    return (
      <div style={style}>
        {React.cloneElement(children, {
          onFocus: this.handleFocus,
          onBlur: this.handleBlur,
        })}
        {results.length && !disabled ? (
          <ResultsContainer>
            <ResultList>
              {this.state.results.map(result => (
                <Result key={result.osm_id} onClick={() => this.select(result)}>
                  {result.display_name}
                </Result>
              ))}
            </ResultList>
          </ResultsContainer>
        ) : null}
      </div>
    );
  }
}
