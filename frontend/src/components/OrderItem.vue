<template>


  <div class="order-row">

      <div class="order-row-container ml-3">
        <div class="order-cols col-id pa-0">
          # {{ order.id }}
        </div>

        <div class="order-cols col-ext-id pa-0">
          ID: {{ order.extId }}
        </div>

        <div class="order-cols col-created-date pa-0">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">Дата создания: {{ order.createdDate }}</span>
            </template>
            <span>Дата создания: {{ order.createdDate }}</span>
          </v-tooltip>
        </div>

        <div v-if="order.updatedDate" class="order-cols col-created-date pa-0">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">Дата обновления: {{ order.updatedDate }}</span>
            </template>
            <span>Дата обновления: {{ order.updatedDate }}</span>
          </v-tooltip>
        </div>

        <div v-else class="order-cols col-created-date pa-0">

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

<!--          <span v-if="order.versionEstimate === 0" class="text-red">-->
<!--            <v-tooltip bottom>-->
<!--              <template v-slot:activator="{ on }">-->
<!--                <v-icon color="red darken-1" v-on="on">mdi-file-document-edit</v-icon>-->
<!--              </template>-->
<!--              <span>Статус: смета в разработке</span>-->
<!--            </v-tooltip>-->
<!--          </span>-->

<!--          <span v-else-if="order.versionEstimate === 1" class="text-green">-->
<!--            <v-tooltip bottom>-->
<!--              <template v-slot:activator="{ on }">-->
<!--                <v-icon color="orange" v-on="on">mdi-file-document</v-icon>-->
<!--              </template>-->
<!--              <span>Статус: посчитана предварительная смета</span>-->
<!--            </v-tooltip>-->
<!--          </span>-->

<!--          <span v-else-if="order.versionEstimate === 2" class="text-green">-->
<!--            <v-tooltip bottom>-->
<!--              <template v-slot:activator="{ on }">-->
<!--                <v-icon color="green darken-2" v-on="on">mdi-file-document-multiple</v-icon>-->
<!--              </template>-->
<!--              <span>Статус: посчитана фактическая смета</span>-->
<!--            </v-tooltip>-->
<!--          </span>-->

        </div>

        <div class="order-cols col-action-order">
          <v-btn icon v-on:click="edit" class="my-1" tile>
            <v-icon dark>mdi-pencil</v-icon>
          </v-btn>

          <Estimate v-bind:extId="order.extId"
                    v-bind:customer="order.customer"
                    v-bind:address="order.address"/>

          <v-btn icon v-on:click="del" class="my-1 mr-1" tile>
            <v-icon dark>mdi-delete</v-icon>
          </v-btn>
        </div>
      </div>

      <div class="order-col-container font-weight-bold ml-3">
        <div class="order-cols col-customer py-1">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span class="font-weight-regular">{{ env.customer }}: </span>
              <span v-on="on">{{ order.customer }}</span>
            </template>
            <span>{{ order.customer }}</span>
          </v-tooltip>
        </div>

        <div class="order-cols col-address py-1 mb-3">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span class="font-weight-regular">{{ env.address }}: </span>
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
import env from '../../env.config.json'

export default {
  name: 'OrderItem',
  props: ['order'],
  components: { Estimate },
  methods: {

    edit() {
      this.$emit('editOrder', this.order)
    },

    del() {
      this.$emit('removeOrder', this.order)
    },

  },
  data: () => ({
    env,
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
  border: 1px solid $border-bottom;
  font-size: small;
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
  background-color: $backgroundOrderItem;
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

.order-col-container {
  //border: 1px solid #ff0000;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

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
  flex-basis: 15%;
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
  flex-basis: 5%;
}

.col-action-order {
  text-align: right;
  flex-basis: 16%;
}

.text-red {
  color: #F8425F;
}
.text-green {
  color: green;
}
</style>