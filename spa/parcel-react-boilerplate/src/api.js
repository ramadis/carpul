import axios from 'axios';

const instance = axios.create({
	baseURL: 'http://3b54813e.ngrok.io/api'
});

instance.interceptors.request.use(config => {
	instance.defaults.headers.common['Authorization'] = localStorage.getItem(
		'token'
	);
	return config;
});
export default instance;
