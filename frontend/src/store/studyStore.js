// import router from "@/router";

import { createStudy, getAllStudy } from "@/api/study.js";

const studyStore = {
  namespaced: true,
  state: {
    isCreated: false,
    studyId: 0,
  },
  getters: {},
  mutations: {
    SET_IS_SUCCESS: (state, isCreated) => {
      state.isCreated = isCreated;
    },
    SET_STUDY_ID: (state, studyId) => {
      state.studyId = studyId;
    },
  },
  actions: {
    async createConfirm({ commit }, studyInfo) {
      await createStudy(
        studyInfo,
        (data) => {
          console.log(data);
          console.log(data.headers);
          console.log(data.body);
          commit("SET_STUDY_ID", data.body);
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
    async getList({ commit }) {
      await getAllStudy(
        (data) => {
          console.log(data);
          console.log(data.headers);
          console.log(data.body);
          commit("SET_STUDY_ID", data.body);
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
  },
};

export default studyStore;
