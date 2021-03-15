<template>

  <div class="order-row">





      <div class="order-row-container">
        <div class="order-cols col-id pa-0">
          # {{ order.id }}
        </div>

        <div class="order-cols col-ext-id pa-0">
          ID: {{ order.extId }}
        </div>

        <div v-if="order.updatedDate === null" class="order-cols col-created-date pa-0">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.createdDate }}</span>
            </template>
            <span>Дата создания: {{ order.createdDate }}</span>
          </v-tooltip>
        </div>

        <div v-else class="order-cols col-created-date pa-0">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.updatedDate }}</span>
            </template>
            <span>Дата создания: {{ order.updatedDate }}</span>
          </v-tooltip>
        </div>

        <div class="order-cols col-verif pa-0">
          <span v-if="order.verificationDate === null" class="text-red">
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-icon color="red darken-1" v-on="on">mdi-alert-circle</v-icon>
              </template>
              <span>Статус: в работе</span>
            </v-tooltip>
          </span>

          <span v-else-if="order.verificationDate !== null" class="text-green">
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-icon color="green darken-2" v-on="on">mdi-check-all</v-icon>
              </template>
              <span>Статус: завершено и проверено</span>
            </v-tooltip>
          </span>
        </div>

        <div class="order-cols col-action-order">
          <v-btn icon v-on:click="edit">
            <v-icon dark>mdi-pencil</v-icon>
          </v-btn>

          <Estimate v-bind:extId="order.extId"
                    v-bind:customer="order.customer"
                    v-bind:address="order.address"/>

          <v-btn icon v-on:click="del">
            <v-icon dark>mdi-delete</v-icon>
          </v-btn>
        </div>
      </div>

      <div class="order-row-container font-weight-bold">
        <div class="order-cols col-customer py-1">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.customer }}</span>
            </template>
            <span>{{ order.customer }}</span>
          </v-tooltip>
        </div>

        <div class="order-cols col-address py-1">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.address }}</span>
            </template>
            <span>{{ order.address }}</span>
          </v-tooltip>
        </div>
      </div>






  </div>

</template>

<script>
import Estimate from '@/components/Estimate'

export default {
  name: 'OrderItem',
  props: ['order', 'editOrder', 'delOrder'],
  components: { Estimate },
  methods: {

    edit() {
      this.$emit('edit', this.order)
    },
    del() {
      this.$emit('removeOrder', this.order.id)
    },

  },
  data: () => ({
    customer: '',
    address: '',
    selection: 'addEstimate'

  }),


}
</script>

<style lang="scss">
@import "src/assets/styles/styles";
.order-row {
  color: $text-color;
  border-bottom: 1px solid $border-bottom;
  font-size: small;
  margin-left: 10px;
  margin-right: 10px;
}

.order-row-container {
  //border: 1px solid #ff0000;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.order-row:hover {
  background: $hover-item;
}

.order-cols {
  //border: 1px solid #ccc;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.col-id {
  flex-basis: 10%;
}

.col-ext-id {
  flex-basis: 10%;
}

.col-created-date {
  flex-basis: 20%;
}

.col-customer {
  flex-basis: 50%;
}

.col-address {
  flex-basis: 50%;
}

.col-verif {
  text-align: right;
  flex-basis: 50%;
}

.col-action-order {
  text-align: right;
  flex-basis: 15%;
}

.text-red {
  color: #F8425F;
}
.text-green {
  color: green;
}
</style>