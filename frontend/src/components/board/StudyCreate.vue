<template>
  <div>
    <el-container>
      <el-main class="outline-box">
        <h2 class="text-h6 mb-3">스터디 만들기</h2>
        <el-form>
          <el-row :gutter="20">
            <el-col><p>제목</p></el-col>
            <el-col><el-input placeholder="제목을 입력하세요." /></el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <p>기업</p>
            </el-col>
            <el-col :span="12">
              <p>직군</p>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-input placeholder="기업을 입력하세요." />
            </el-col>
            <el-col :span="12">
              <el-select
                v-model="value"
                class="m-2"
                placeholder="직군을 선택하세요."
                size=""
              >
                <el-option
                  v-for="item in deptOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col><p>날짜</p></el-col>
            <el-col> </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <p>인원</p>
            </el-col>
            <el-col :span="12">
              <p>면접 유형</p>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-select
                v-model="value"
                class="m-2 select"
                placeholder="인원 수를 선택하세요."
                size=""
              >
                <el-option
                  v-for="item in capacityOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
            <el-col :span="12">
              <el-select
                v-model="value"
                class="m-2"
                placeholder="면접 유형을 선택하세요."
              >
                <el-option
                  v-for="item in studyOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-button
              color="#c7cde6"
              class="mt-10 mb-10"
              size="large"
              style="margin: 0 auto; margin-top: 3%"
              @click="dialogVisible = true"
            >
              채점 템플릿 선택
            </el-button>
            <el-dialog
              class="el-dialog"
              v-model="dialogVisible"
              width="600px"
              style="border-radius: 5%"
            >
              <el-table
              class="el-table"
                :span-method="objectSpanMethod"
                :data="tableData"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                :class="tableRowClassName"
              >
                <el-table-column type="selection" width="35"></el-table-column>
                <el-table-column prop="type" label="대분류" width="120" />
                <el-table-column prop="name1" label="항목" />
              </el-table>
              <p style="margin-top: 3%; text-align: center; color: red">
                * 최소 1개 이상의 대분류를 선택해주세요.
              </p>
              <el-button        
              block
              color="#9DADD8"
              size="large"
              style="margin-top: 3%; margin-left: 35%; width: 25%;"
            >
              완료
            </el-button>
            </el-dialog>
          </el-row>

          <el-row
            :gutter="20"
            style="display: flex; justify-content: space-around"
          >
            <el-col><p>진행시간</p></el-col>

            <el-checkbox-group>
              <el-checkbox label="1시간" />
              <el-checkbox label="2시간" />
              <el-checkbox label="3시간" />
              <el-checkbox label="4시간" />
            </el-checkbox-group>
          </el-row>

          <el-row :gutter="20">
            <el-col><p>소개</p></el-col>
            <el-col
              ><el-input
                type="textarea"
                maxlength="300"
                v-model="textarea"
                class="text-area"
                :autosize="{ minRows: 5, maxRows: 5 }"
                show-word-limit
              ></el-input
            ></el-col>
          </el-row>

          <el-row :gutter="20">
            <el-button
              block
              color="#9DADD8"
              class="mt-10 mb-10 confirm-btn"
              size="large"
              style="margin: 0 auto; margin-top: 3%"
            >
              완료
            </el-button>
          </el-row>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
// import { parseHeight } from "element-plus/es/components/table/src/util";
import { ref } from "vue";

const checkList = "1시간";
const multipleSelection = ref([]);

export default {
  name: "UserSignup",
  components: {},
  methods: {
    handleSelectionChange(val) {
      multipleSelection.value = val;
    },
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 1) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          };
        }
      }
    },
    tableRowClassName({ rowIndex, columnIndex }) {
      if (columnIndex === 1 || columnIndex === 2 || rowIndex === 1) {
        console.log(rowIndex);
        return 'warning-row'
      }
      return ''
    },
  },
  data() {
    return {
      dialogVisible: false,
      text: ref(""),
      textarea: ref(""),
      checkList,
      studyOptions: [
        {
          value: "일대다",
          label: "일대다",
        },
        {
          value: "다대다",
          label: "다대다",
        },
      ],
      deptOptions: [
        {
          value: "연구직",
          label: "연구직",
        },
        {
          value: "소프트웨어",
          label: "소프트웨어",
        },
        {
          value: "영업마케팅직",
          label: "영업마케팅직",
        },
        {
          value: "경영지원직",
          label: "경영지원직",
        },
        {
          value: "디자인직",
          label: "디자인직",
        },
        {
          value: "기술직",
          label: "기술직",
        },
      ],
      capacityOptions: [
        {
          value: "1",
          label: "1",
        },
        {
          value: "2",
          label: "2",
        },
        {
          value: "3",
          label: "3",
        },
        {
          value: "4",
          label: "4",
        },
        {
          value: "5",
          label: "5",
        },
        {
          value: "6",
          label: "6",
        },
      ],
      tableData: [
        {
          type: "태도",
          name1: "자신감 있는 표정과 목소리인가?",
        },
        {
          type: "태도",
          name1: "기본 준비 자세가 올바른가?",
        },
        {
          type: "직무역량",
          name1: "지원한 직무에 대한 구체적인 이해도를 가졌는가?",
        },
        {
          type: "직무역량",
          name1: "직무 수행에 필요한 역량을 갖췄는가?",
        },
        {
          type: "팀워크",
          name1: "다양한 의견을 수렴한 경험이 있는가?",
        },
        {
          type: "팀워크",
          name1: "적극적으로 참여하는 자세를 가졌는가?",

        },
        {
          type: "문제해결",
          name1: "문제를 해결하고자 하는 열정, 끈기, 의지를 가졌는가?",
        },
        {
          type: "문제해결",
          name1: "해결에 도움이 되는 방향성을 제시하였는가?",
        },
        {
          type: "기업이해도",
          name1: "기업에 대한 이해도가 높은가?",
        },
        {
          type: "기업이해도",
          name1: "퇴사 가능성이 있는가?",
        },
      ],
    };
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
}
.el-container {
  margin: 0 auto;
  margin-top: 2%;
  margin-bottom: 2%;
  width: 45%;
}

p {
  /* float: left; */
  margin: 30px 0 10px 0;
}
.el-dialog{
  display: flex;
}
.confirm-btn {
  color: white;
  width: 30%;
}
.el-button:not(.confirm-btn) {
  width: 96%;
  margin-top: 30px;
  color: white;
}
.el-select {
  width: 100%;
}
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
</style>
