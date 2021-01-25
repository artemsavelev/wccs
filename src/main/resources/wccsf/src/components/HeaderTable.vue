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
        №
      </div>

      <div class="main-container-wrapper pa-1 column-700">
        Наименование
      </div>
      <div class="main-container-wrapper pa-1 column-100">
        Ед. изм.
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        Кол-во
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        Цена
      </div>



      <div class="main-container-wrapper pa-1 column-100" v-if="type !== 123">
        Стоимость
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
export default {
  name: "HeaderTable",
  props: ['typeSection', 'type'],

  data() {
    return {




    }
  },
}
</script>

<style scoped>

</style>