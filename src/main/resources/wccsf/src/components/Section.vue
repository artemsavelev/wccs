<template>

  <div class="main-section mb-3 font-s">
    <div class="outer-container">

      <div class="inner-container">
        <div class="inner-container-item column-1150 brd-bottom-2">
          {{  }}. {{  }}:
        </div>
      </div>

      <div class="inner-container grey lighten-2 font-weight-medium">
        <div class="inner-container-item column-50 pl-1 brd-bottom brd-right brd-left-2">
          {{ env.id }}
        </div>
        <div class="inner-container-item column-700 pl-1 brd-bottom brd-right">
          {{ env.name }}
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right">
          {{ env.dimension }}
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right">
          {{ env.quantity }}
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right">
          {{ env.price }}
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right-2">
          {{ env.sum }}
        </div>
      </div>

      <div class="inner-container font-weight-light">
        <div class="inner-container-item column-50 pl-1 brd-bottom brd-right brd-left-2">
          1
        </div>
        <div class="inner-container-item column-700 pl-1 brd-bottom brd-right">
          2
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right">
          3
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right">
          4
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right">
          5
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom brd-right-2">
          6
        </div>
      </div>

      <div class="inner-container grey lighten-2 font-weight-medium">
        <div class="inner-container-item column-1050 pl-1 pr-1 brd-right brd-left-2 t-align-right brd-bottom-2">
          ИТОГО {{  }} :
        </div>
        <div class="inner-container-item column-100 pl-1 brd-bottom-2 brd-right-2">
          {{ value.toLocaleString('ru-RU') }}
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import env from '../../env.json'

export default {
  name: "Section",
  data() {
    return {
      value: 100000000,
      env

    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style scoped lang="scss">

</style>