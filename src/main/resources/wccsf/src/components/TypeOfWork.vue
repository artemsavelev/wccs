<template>
  <div>
    <div class="main font-s mt-5">
      <div class="font-s mt-1">
        Виды работ:
      </div>
      <div class="font-s mt-0">
        {{ this.text.typeOfWork }}
      </div>
      <div class="font-s mt-0">
        {{ this.text.text }}
      </div>
      <ModalWin v-on:add="addTypeOfWork"
                v-bind:typeSection="typeSection"/>

    </div>
  </div>
</template>

<script>
import ModalWin from "./ModalWin";

export default {
  name: "TypeOfWork",
  components: { ModalWin },
  data() {
    return {
      typeSection: 0,
      text: {}

    }
  },
  methods: {
    addTypeOfWork(text) {
      this.text = text
      console.log(this.text)
    }
  }
}
</script>

<style scoped>

</style>