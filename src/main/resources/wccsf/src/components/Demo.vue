<template>
  <v-dialog v-model="dialog" width="70%"  scrollable persistent>

    <template v-slot:activator="{ on }" class="mt-1">
      <v-btn medium v-on="on" class="ml-5" color="primary" tile>предпросмотр</v-btn>
    </template>
    <v-card>

      <v-card-title class="grey lighten-2">
        <span class="font-xl ml-16">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>{{ mdiClose }}</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <v-container style="height: 120vh">
          <div>

            <section>
              <div>
                <div class="font-xl ml-14 font-weight-medium">
                  Смета
                </div>
                <div class="ml-14">
                  по заявке:
                </div>
                <div class="ml-14">
                  по адресу:
                </div>
                <div class="ml-14">
                  от компании:
                </div>
                <div class="ml-14">
                  составил:
                </div>
              </div>
            </section>

            <section>
              <div>
                <div class="ml-14">
                  Виды работ:
                </div>
                <div class="ml-14">
                  ***
                </div>
              </div>
            </section>

            <section>
              <Section/>
            </section>

            <section>
              <Section/>
            </section>

            <section>
              <Section/>
            </section>

            <section>
              <div class="main-section mb-3 font-s">
                <div class="outer-container grey lighten-2">
                  <div class="inner-container font-s font-weight-bold">
                    <div class="inner-container-item column-1050 pl-1 pr-1 brd-right brd-left-2 t-align-right brd-bottom-2 brd-top-2">
                      ИТОГО {{  }} :
                    </div>
                    <div class="inner-container-item column-100 pl-1 brd-bottom-2 brd-right-2 brd-top-2">
                      000 000 000
                    </div>
                  </div>
                </div>
              </div>
            </section>

          </div>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import { mdiClose } from '@mdi/js';
import Section from "@/components/Section";

export default {
  name: "Demo",
  components: { Section },
  data() {
    return {
      mdiClose,
      formTitle: 'Пердпросмотр',
      dialog: false,
      typeSection: 4,
    }
  },
  methods: {
    close() {
      this.dialog = false
    }
  }
}
</script>

<style lang="scss">
.main-section {
  display: flex;
  flex-direction: row;
  justify-content: center;
  color: black;
}

.outer-container {

}

.inner-container {
  display: flex;
  &-item {

  }
}

.t-align-right {
  text-align: right;
}

.brd-top {
  border-top: 1px solid black;
}

.brd-top-2 {
  border-top: 2px solid black;
}

.brd-bottom {
  border-bottom: 1px solid black;
}

.brd-bottom-2 {
  border-bottom: 2px solid black;
}

.brd-left {
  border-left: 1px solid black;
}

.brd-left-2 {
  border-left: 2px solid black;
}

.brd-right {
  border-right: 1px solid black;
}
.brd-right-2 {
  border-right: 2px solid black;
}

.column-1050 {
  width: 1050px;
  max-width: 1050px;
}

.column-1150 {
  width: 1150px;
  max-width: 1150px;
}
</style>