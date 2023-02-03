import http from "./http.js";

const api = http;

async function createStudy(info, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");

  await api
    .post(`/users/study`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function getAllStudy(success, fail) {
  await api.post(`/users/study`).then(success).catch(fail);
}

export { createStudy, getAllStudy };
