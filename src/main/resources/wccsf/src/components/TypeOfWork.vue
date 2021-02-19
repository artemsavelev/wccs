<template>
  <div>
    <div class="main font-s mt-5">
      <div class="font-s mt-1">
        {{ env.typeWork }}:
      </div>
      <div class="font-s mt-0">
        {{ this.description.workDescription }}
      </div>
      <div class="font-s mt-0">
        {{ this.description.comment }}
      </div>
      <ModalForm v-on:add="addWorkDescription"
                v-bind:ex="ex"
                v-bind:typeSection="typeSection"/>

    </div>
  </div>
</template>

<script>
import env from "../../env.config.json"
const ModalForm = () => import('./ModalForm')

export default {
  name: "TypeOfWork",
  props: ['ex'],
  components: { ModalForm },
  data() {
    return {
      env,
      typeSection: 0,
      description: {}
    }
  },
  methods: {
    addWorkDescription(text) {
      this.description = text
      this.$emit('transmit', this.description)
    }
  }
}
</script>

<style scoped>

</style>