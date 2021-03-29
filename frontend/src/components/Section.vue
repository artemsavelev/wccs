<template>
  <div class="data">
    <div class="data-row">
      <div class="data-cols col-idx pl-2">
        {{ idx + 1 }}
      </div>
      <div class="data-cols col-name">
        {{ item.name }}
      </div>
      <div class="data-cols col-dimension">
        {{ item.dimension }}
      </div>
      <div class="data-cols col-quantity">
        {{ item.quantity }}
      </div>
      <div class="data-cols col-price">
        {{ item.price | format }}
      </div>
      <div class="data-cols col-sum">
        {{ item.price * item.quantity | format }}


      </div>
      <div class="data-cols col-action-section">

      </div>
    </div>
  </div>
</template>

<script>
import env from '../../env.config.json'


export default {
  name: 'Section',
  props: ['item', 'idx'],
  computed: {},
  data() {
    return {
      env,
      value: 100000000,
      sum: 0

    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style lang="scss">

.data-row {
  border-bottom: 1px solid gray;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.data-cols {
  height: auto;
  text-wrap: normal;
}




</style>