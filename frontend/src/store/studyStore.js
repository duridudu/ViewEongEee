// import router from "@/router";

import { createStudy } from "@/api/study.js";

const studyStore = {
  namespaced: true,
  state: {},
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },

    CLEAR_USER_INFO: (state) => {
      state.isLogin = false;
      state.isLoginError = false;
      state.userInfo = null;
      state.isValidToken = false;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await createStudy(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["accessToken"];
            let refreshToken = data["refreshToken"];
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default studyStore;
