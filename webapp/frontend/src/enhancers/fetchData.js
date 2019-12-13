import React, { Component } from "react";
import axios from "axios";
import config from "../../config";

const fetchData = options => Comp => {
  return class FetchData extends Component {
    state = {
      loading: false,
      data: null,
    };

    async componentDidMount() {
      this.setState({ loading: true });
      const props = { ...config, ...this.props };
      const result = await axios.get(options.url(props));
      this.setState({ loading: false, data: options.extract(result) });
    }

    render() {
      const { loading, data } = this.state;
      return <Comp loading={loading} data={data} {...this.props} />;
    }
  };
};

export default fetchData;
