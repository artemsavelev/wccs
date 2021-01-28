<template>
    <v-dialog v-if="profile" v-model="dialog" scrollable width="80%">

        <template v-slot:activator="{ on }" class="mt-10">
            <v-btn :disabled="$route.path === '/profile' || $route.path === '/admin' || $route.path === '/settings'" dark v-on="on" text tile>CREATE</v-btn>
        </template>
        <v-card>

            <v-card-title class="headline grey lighten-2">
                <span class="header-modal ml-3">{{formTitle}}</span>
            </v-card-title>

            <v-card-text>
                <v-container>

                    <v-row>
                        <v-col cols="12" sm="6" md="4">
                            <v-text-field
                                    dense
                                    label="Номер заявки"
                                    v-model="extId"/>
                        </v-col>

                        <v-col cols="12" sm="6" md="4">
                            <v-text-field
                                    dense
                                    label="Заказчик"
                                    v-model="customer"/>
                        </v-col>



                        <v-col cols="12" sm="6" md="4">
                            <v-text-field
                                    dense
                                    label="Адрес"
                                    v-model="address" />
                        </v-col>
                    </v-row>

                </v-container>
                <v-card-actions>
                    <v-btn v-on:click="save" color="primary" class="mr-2" tile>save</v-btn>
                    <v-btn v-on:click="close" color="primary" tile>close</v-btn>
                </v-card-actions>
            </v-card-text>

        </v-card>
    </v-dialog>

</template>

<script>

import {mapActions, mapGetters} from "vuex";

    export default {
        name: "OrderForm",
        computed: {
            ...mapGetters(['profile']),

            formTitle() {
                return this.editedIndex === -1 ? 'Добавление записи' : 'Редактирование записи '
            },
        },
        data() {
            return {

                dialog: false,
                orderId: '',
                extId: '',
                customer: '',
                address: '',
                creationDate: '',
                editedIndex: -1
            };
        },

        methods: {
            ...mapActions(['addOrder', 'addOrderMutation']),
            save() {

                //console.log(this.startDate + ' ' + this.startTime + ':' + new Date().getSeconds() + '-0300')

                const order = {
                    // id: this.id,
                    extId: this.extId,
                    customer: this.customer,
                    address: this.address,

                    // endDate: this.endDate + ' ' + this.endTime + ':' + new Date().getSeconds(),
                };

                this.addOrder(order);
                console.log(order);

            },
            close() {
                this.dialog = false;
            }
        },
        watch: {

            orderVal(newVal){

                this.dialog = true;
                this.id = newVal.id;
                this.extId = newVal.extId;
                this.creationDate = newVal.creationDate;
                // this.verificationName = newVal.verificationName;
                // this.verificationDate = newVal.verificationDate;
                this.customer = newVal.customer;
                this.editedIndex = this.id
            }




        },


    }




</script>

<style scoped lang="scss">
    .container-form {
        margin: auto 0;
    }

</style>