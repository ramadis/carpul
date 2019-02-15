import axios from 'axios';

const instance = axios.create({
	baseURL: 'http://3b54813e.ngrok.io/api'
});

export default instance;
