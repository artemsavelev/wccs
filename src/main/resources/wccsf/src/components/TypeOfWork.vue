<template>
  <div>
    <div class="main font-s mt-5">
      <div class="font-s mt-1">
        {{ env.typeWork }}:
      </div>
      <div class="font-s mt-0">
        {{ this.workDescription.typeOfWork }}
      </div>
      <div class="font-s mt-0">
        {{ this.workDescription.text }}
      </div>
      <ModalWin v-on:add="addWorkDescription"
                v-bind:ex="ex"
                v-bind:typeSection="typeSection"/>

    </div>
  </div>
</template>

<script>
import env from "../../env.config.json"
import ModalWin from "./ModalWin";

export default {
  name: "TypeOfWork",
  props: ['ex'],
  components: { ModalWin },
  data() {
    return {
      env,
      typeSection: 0,
      workDescription: {}
    }
  },
  methods: {
    addWorkDescription(text) {
      this.workDescription = text
      this.$emit('transmit', this.workDescription)
    }
  }
}
</script>

<style scoped>

</style>