import axios from "axios";

const baseURL = "http://localhost:";

const axiosAuthApi = axios.create({
    baseURL,
    timeout: 5000,
});

axiosAuthApi.interceptors.request.use(
    axiosAuthApi.interceptors.request.use(
        async (config) => {
            // 요청을 보내기 전에 실행 할 것
            const { access_token } = await GetToken();
            if (access_token) {
                config.headers[X_AUTH_TOKEN] = `${access_token}`;
            }
            return config;
        },

        (error) => {
            // 에러가 발생하기 전에 실행 할것
            return Promise.reject(error);
        }
    )
);
