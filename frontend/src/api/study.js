import http from "./http.js";

const api = http;

async function studyCreate(user, success, fail) {
  await api
    .post(`/users/signin`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

export { studyCreate };
