<template>
  <div class="section-row font-s">
    <div class="font-weight-bold mb-1" v-if="typeSection === 1">
      {{ env.sectionDevice }}
    </div>
    <div class="font-weight-bold mb-1" v-else-if="typeSection === 2">
      {{ env.sectionMaterial }}
    </div>
    <div class="font-weight-bold mb-1" v-else-if="typeSection === 3">
      {{ env.sectionWork }}
    </div>


    <div class="section-row-container headerTable color-text">
      <div class="section-cols col-idx pl-2 pointer" @click="sortId">
        {{ env.id }}
      </div>
      <div class="section-cols col-name pointer" @click="sortName">
        {{ env.name }}
      </div>
      <div class="section-cols col-dimension">
        {{ env.dimension }}
      </div>
      <div class="section-cols col-quantity">
        {{ env.quantity }}
      </div>
      <div class="section-cols col-price">
        {{ env.price }}
      </div>
      <div class="section-cols col-sum" v-if="type !== 123">
        {{ env.sum }}
      </div>
      <div class="section-cols col-action-section" v-if="type !== 123">
      </div>

      <div class="section-cols col-comment" v-if="type === 123">
        Комментарий
      </div>
      <div class="section-cols col-action-section" v-if="type === 123">
      </div>

    </div>
  </div>
</template>

<script>
import env from "../../env.config.json"

export default {
  name: "HeaderTable",
  props: ['typeSection', 'type', 'sortId', 'sortName'],
  computed: {

  },

  data() {
    return {
      env,

    }
  },
}
</script>

<style scoped>
.pointer {
  cursor: pointer;
}

.color-text {
  color: white;
}
</style>