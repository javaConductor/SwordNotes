import axios from "axios";

export default (function (axios) {
  axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

  function emptyString(s){
    if( !s )
      return true;
    return s.trim().length === 0;
  }

  class Backend {

    constructor({hostname, port} = {}) {
      this.hostname = hostname || 'localhost';
      this.port = port || 8080;
      this.accessToken = '';
      this.refreshToken = '';
    }

    async login(username, password) {
      console.log(`backend.login: ${username},${password}`);
      return this.callBackend(`api/login?username=${username}&password=${password}`, 'POST', {}, {}).then((response) => {
        const access_token = response.data.access_token;
        const refresh_token = response.data.refresh_token;
        this.refreshToken = refresh_token;
        this.accessToken = access_token;
        return [{
          accessToken: access_token,
          refreshToken: refresh_token}];
      }, (err) => {
        return [null, `authentication error: ${err}`];
      });
    }

    async logout() {
      console.log(`backend.logout`);
      this.accessToken = '';
      this.refreshToken = '';
      return null;
    }

    authenticated() {
      console.log(`backend.authenticated: tokens: ${this.accessToken}:${this.refreshToken}`);
      const authOk = !emptyString( this.accessToken ) && !emptyString( this.refreshToken );
      console.log(`backend.authenticated: ${authOk}`);
      return authOk;
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
