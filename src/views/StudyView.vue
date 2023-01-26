<template>
  <div class="main">
    <MyHeader></MyHeader>
    <StudyDetail></StudyDetail>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MyHeader from "@/components/common/MyHeader.vue";
import StudyDetail from "@/components/board/StudyDetail.vue";

export default defineComponent({
  name: "StudyView",
  components: {
    MyHeader,
    StudyDetail,
  },
});
</script>
