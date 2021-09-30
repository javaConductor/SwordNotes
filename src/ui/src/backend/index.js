import axios from "axios";

export default (function (axios) {
  axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

  class Backend {

    constructor({hostname, port} = {}) {
      this.hostname = hostname || 'localhost';
      this.port = port || 8080;
      this.accessToken = null;
      this.refreshToken = null;
    }

    async login(username, password) {
      console.log(`backend.login: ${username},${password}`);
      return this.callBackend(`api/login?username=${username}&password=${password}`, 'POST', {}, {}).then((response) => {
        const {access_token: accessToken, refresh_token: refreshToken} = response.data;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        return [{accessToken, refreshToken}];
      }, (err) => {
        return [null, `authentication error: ${err}`];
      });
    }

    logout() {
      this.accessToken = null;
      this.refreshToken = null;
    }

    authenticated() {
      return this.accessToken && this.accessToken.length > 0 && this.refreshToken && this.refreshToken.length > 0;
    }

    async callBackend(path, method, headers, data) {
      const defaultHeaders = {'Content-Type': 'application/json'};
      return axios({
        method: method,
        url: `http://${this.hostname}:${this.port}${path ? '/' : ''}${path}`,
        data: data,
        config: {headers: {...defaultHeaders, ...headers}}
      });
    }
  }

  return new Backend();

})(axios);
