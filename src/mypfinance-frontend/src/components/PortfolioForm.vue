<template>
  <v-form class="mt-1" ref="portfolioform">
    <v-container>
      <v-row>
        <v-col cols="12" md="6" class="py-0 ma-0">
          <input type="hidden" v-model="portfolioPosition.stockPositionId" />
          <v-textarea
            v-model="portfolioPosition.stockSymbol"
            placeholder="Symbol"
            required
            class="ma-0 pa-0 form-label"
            dense
          ></v-textarea>
        </v-col>
        <v-col cols="12" md="6" class="py-0 ma-0">
          <v-textarea
            v-model="portfolioPosition.stockName"
            placeholder="Name"
            required
            class="ma-0 pa-0 form-label"
            dense
          ></v-textarea>
        </v-col>
        <v-col cols="12" md="6" class="py-0 ma-0">
          <v-text-field
            v-model="portfolioPosition.stockPrice"
            placeholder="Price per unit"
            required
            class="ma-0 pa-0 form-label"
            dense
            :rules="[required('Price per unit'), minValue('Price per unit', 0.01)]"
          ></v-text-field>
        </v-col>
        <v-col cols="12" md="6" class="py-0 ma-0">
          <v-text-field
            v-model="portfolioPosition.stockUnits"
            placeholder="Units"
            required
            class="ma-0 pa-0 form-label"
            dense
            :rules="[required('Units'), minValue('Units', 0.01)]"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="justify-end">
        <v-btn
          outlined
          small
          class="blue--text font-weight-bold"
          :loading="loading"
          @click="() => { if (!this.$refs.portfolioform.validate()) return; onSubmitClick(); }"
        >Submit</v-btn>
        <v-btn
          v-if="showCloseButton"
          outlined
          small
          class="ml-2 blue--text font-weight-bold"
          @click="onCloseClick"
        >Close</v-btn>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import validations from '@/helpers/validation'

export default {
  props: {
    portfolioPosition: {
      type: Object
    },
    showCloseButton: {
      type: Boolean,
      default: false
    },
    onSubmitClick: {
      type: Function
    },
    onCloseClick: {
      type: Function
    },
    loading: {
      type: Boolean
    }
  },
  data () {
    return {
      dateMenu: false,
      ...validations
    }
  },
  methods: {
    reset () {
      this.$refs.portfolioform.reset()
    }
  }
}
</script>

<style>
</style>
