<template>
  <div class="main">
    <MyHeader></MyHeader>
    <MainCenter />
    <MainCardList />
    <!--<SearchBar /> -->
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MainCenter from "@/components/mainpage/MainCenter.vue";
import MainCardList from "@/components/mainpage/MainCardList.vue";
import MyHeader from "@/components/common/MyHeader.vue";

export default defineComponent({
  name: "MainView",
  components: {
    MyHeader,
    MainCenter,
    MainCardList,
  },
});
</script>
<style scoped>
.MainCenter {
  text-align: center;
}
</style>
