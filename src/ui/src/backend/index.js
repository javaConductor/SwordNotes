import axios from "axios";
import Messages from "@/common/messages";

const {v4: uuid} = require('uuid');

export default (function (axios, uuid, messages) {
  axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

  class Backend {

    constructor({hostname, port} = {}) {
      this.hostname = hostname || 'localhost';
      this.port = port || 9999;
      this.nodes = [];
    }

    async saveNode(node) {
      console.log(`backend.saveNode: ${JSON.stringify(node)}`);
      return this.callBackend('/node', 'POST', {}, node).then((response) => {
        let responseData = response.data[0];
        return [responseData];
      }, (err) => {
        return [null, err];
      });
    }

    async getNodes() {
      return this.callBackend('/node', 'GET',).then((response) => {
        let responseData = response.data;
        this.nodes = responseData;//.filter((n) => (n.uuid));
        return [this.nodes];
      }, (err) => {
        const m = `Error getting nodes: ${err}.`;
        messages.addError(m);
        return [null, err];
      });
    }

    async removeNode(nodeUUID) {
      return this.callBackend(`/node/${nodeUUID}`, 'DELETE',).then((response) => {
        let responseData = response.data;
        return [responseData];
      }, (err) => {
        const m = `Error removing node [${nodeUUID}]: ${err.toString()}.`;
        messages.addError(m);
        return [null, err];
      });
    }


    async getPipelines() {
      return this.callBackend('/pipeline', 'GET',).then(async (response) => {
        let pipelines = response.data;
        const m = `Loaded ${pipelines.length} pipelines.`;
        messages.addMessage(m);
        return [pipelines];

      }, (err) => {
        const m = `Error reading pipelines ${err}.`;
        messages.addError(m);
        return [null, err];
      });
    }

    async getPipeline(pipelineName) {
      {
        pipelineName
      }
    }

    /**
     * Run the pipeline and return Promise of results
     *
     * @param pipelineUUID
     * @param values
     * @returns {Promise<*[]|[null, any]>}
     */
    async runPipeline(pipelineUUID, values) {
      return await this.callBackend(`/pipeline/${pipelineUUID}/execute`, "POST", {}, values).then(async (response) => {
        let executionResponse = response.data;
        console.log(`runPipeline(${pipelineUUID}, '${JSON.stringify(values)}' \n-> ${JSON.stringify(executionResponse.results)}`);
        return [executionResponse.results];
      }, (err) => {
        console.warn(`runPipeline(${pipelineUUID}, '${JSON.stringify(values)}'\n-> ${JSON.stringify(err)}`);
        return [null, err];
      })
    }

    /**
     *
     * @param pipelineName
     * @param values
     * @param eventFn
     * @returns {Promise<void>}
     */
    async startPipeline(pipelineName, values, eventFn = (statObj) => (statObj)) {
      {
        pipelineName, values, eventFn
      }
    }

    async callBackend(path, method, headers, data) {
      //console.log(`callBackend: ${path}: ${JSON.stringify(data)}`)
      const defaultHeaders = {'Content-Type': 'application/json'};
      return axios({
        method: method,
        url: `http://${this.hostname}:${this.port}${path}`,
        data: data,
        config: {headers: {...defaultHeaders, ...headers}}
      });
    }

    async savePipeline(pipeline) {
      console.log(`backend.savePipeline: ${JSON.stringify(pipeline.steps)}`);

      // create a new pipeline replacing the step.node with step.nodeUUID
      const newSteps = pipeline.steps.map((step) => {
        return (step.node) ? {...step, node: null, nodeUUID: step.node.uuid} : {...step}
      });

      console.log(`backend.savePipeline: Steps: ${JSON.stringify(newSteps)}`);
      const copy = {...pipeline, steps: newSteps};
      return this.callBackend('/pipeline', 'POST', {}, copy).then((response) => {
        let responseData = response.data;
        console.log(`backend.savePipeline: ${JSON.stringify(pipeline.uuid)}`);
        return [responseData];
      }, (err) => {
        return [null, err];
      });
    }
  }

  return new Backend();

})(axios, uuid, Messages);
