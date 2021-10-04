<template>
  <v-dialog v-model="dialog" width="350px" scrollable persistent>
    <v-card class="rounded-0">
      <v-card-title>
        <div class="font-m font-weight-light">Требуется ввести номер задачи</div>
      </v-card-title>
      <v-card-text class="pl-6 font-s">
        <v-col cols="12" sm="6" md="12" class="ml-0 pl-0">
          <v-text-field
              ref="field"
              hide-details="auto"
              class="rounded-0 styled-input font-s"
              dense
              outlined
              :label="env.numberOrder + '*'"
              v-model="extId"
              required/>
        </v-col>

        <v-btn @click="save(extId)" :color="colorSave" tile height="35" small outlined>{{ env.keySave }}</v-btn>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import env from '../../env.config.json'
import {mapActions} from 'vuex'

export default {
  name: "ExtId",
  props: ['visibleExtId'],
  data: () => ({
    env,
    dialog: false,
    extId: '',
    colorSave: 'primary',
  }),
  watch: {
    visibleExtId(newVal) {
      this.dialog = newVal
    }
  },
  methods: {
    ...mapActions(['showSnack']),
    save(item) {
      if (this.extId) {
        this.dialog = false
        this.$emit('sendValue', item)
        this.$emit('visibleExtId', false)
        this.extId = ''
      } else {
        this.$refs.field.validate()
        const data = {
          message: 'Поля формы не должны быть пустыми',
          color: 'error',
          icon: 'mdi-alert-circle'
        }
        this.showSnack(data)
      }

    }
  }
}
</script>

<style scoped>

</style>