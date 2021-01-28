<template>
  <div class="main font-s mt-1">

    <div class="font-weight-bold mb-1" v-if="typeSection === 1">
      1. Активное оборудование
    </div>
    <div class="font-weight-bold mb-1" v-else-if="typeSection === 2">
      2. Материалы и оборудование
    </div>
    <div class="font-weight-bold mb-1" v-else-if="typeSection === 3">
      3. Работы
    </div>



    <div class="main-container blue-grey lighten-4">


      <div class="main-container-wrapper pa-1 column-80">
        {{ env.id }}
      </div>
      <div class="main-container-wrapper pa-1 column-700">
        {{ env.name }}
      </div>
      <div class="main-container-wrapper pa-1 column-100">
        {{ env.dimension }}
      </div>
      <div class="main-container-wrapper pa-1 column-100">
        {{ env.quantity }}
      </div>
      <div class="main-container-wrapper pa-1 column-100">
        {{ env.price }}
      </div>



      <div class="main-container-wrapper pa-1 column-100" v-if="type !== 123">
        {{ env.sum }}
      </div>

      <div class="main-container-wrapper pa-1 column-200" v-if="type !== 123">
        Комментарий
      </div>

      <div class="main-container-wrapper pa-1 column-700" v-if="type === 123">
        Комментарий
      </div>



    </div>
  </div>
</template>

<script>
import env from '../../env.json'

export default {
  name: "HeaderTable",
  props: ['typeSection', 'type'],

  data() {
    return {
      env
    }
  },
}
</script>

<style scoped>

</style>