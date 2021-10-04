<template>
  <div>
    <div class="main font-s mt-5">
      <div class="font-s mt-1">
        {{ env.typeWork }}:
      </div>
      <div class="font-s mt-0">
        <div v-for="items in this.localDescription">
          <i>{{ items }}</i>
        </div>
      </div>
      <div class="font-s mt-0 red--text">
        <i>{{ this.description.comment }}</i>
      </div>
      <ModalForm @addParentFormDescription="addWorkDescription"
                 :ex="ex"
                 :descriptionFromDb="descriptionFromDb"
                 :typeSection="typeSection"/>

    </div>
  </div>
</template>

<script>
import env from '../../env.config.json'
const ModalForm = () => import('./ModalForm')

export default {
  name: "WorkDescription",
  props: ['ex', 'descriptionFromDb'],
  components: { ModalForm },
  watch: {
    descriptionFromDb(newVal) {
      if (newVal !== null) {
        this.description.workDescription = newVal
        this.$emit('transmit', this.description.workDescription)
      } else {
        this.description = ''
      }
    },
    ex(val) {
      if (val) {
        this.description.comment = env.preliminary + env.priceTimeout
      } else {
        this.description.comment = env.priceTimeout
      }
    }
  },
  mounted() {
  },
  data() {
    return {
      env,
      typeSection: 0,
      description: {},
      localDescription: []
    }
  },
  methods: {
    addWorkDescription(text) {

      const description = text.workDescription.split('\n').map(e => {
        if (e.length > 4) {
          return e
        } else {
          return null
        }
      })

      const index = description.indexOf(null);

      if (index > -1) {
        description.splice(index, description.length);
      }

      this.localDescription = description

      this.description = {
        workDescription: description.join('\n'),
        comment: text.comment
      }
      this.$emit('transmit', this.description)
    }
  }
}
</script>

<style scoped>

</style>