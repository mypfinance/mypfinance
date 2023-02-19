<template>
  <div>
    <v-container>
      <v-layout row>
        <v-flex xs12>
          <v-data-table :headers=headers :items=portfolioPositions sort-by='date' class="elevation-1">
            <template v-slot:top>
              <div class="d-flex align-center pa-4">
                <span class="blue--text font-weight-medium">Portfolio Positions</span>
                <v-divider class="mx-2 my-1" inset vertical style="height: 20px"></v-divider>
                <v-spacer></v-spacer>
                <v-dialog v-model="dialog" max-width="650px">
                  <template v-slot:activator="{ on }">
                    <v-btn outlined small class="blue--text font-weight-bold" v-on="on">New Position</v-btn>
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="text-h5">{{ formTitle }}</span>
                    </v-card-title>

                    <v-card-text>
                      <PortfolioForm
                        :portfolioPosition="editedStockPortfolio"
                        :showCloseButton="true"
                        :onCloseClick="close"
                        :onSubmitClick="savePosition"
                        :loading="loading"
                        ref="form"
                      />
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </div>
            </template>
            <template v-slot:item.value="{ item }">
              <span>{{ `BGN ${item.value.toFixed(2)}` }}</span>
            </template>
<!--            <template v-slot:item.action="{ position }">-->
<!--              <v-icon small class="mr-2" @click="editPosition(position)">edit</v-icon>-->
<!--              <v-icon small @click="deletePosition(position)">delete</v-icon>-->
<!--            </template>-->
          </v-data-table>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import PortfolioForm from '@/components/PortfolioForm'

export default {
  components: { PortfolioForm },
  data () {
    return {
      loading: false,
      dialog: false,
      headers: [
        { text: 'Id', value: 'id', align: ' d-none' },
        { text: 'Symbol', value: 'stockSymbol' },
        { text: 'Name', value: 'stockName' },
        { text: 'Purchase Price', value: 'stockPrice' },
        { text: 'Units', value: 'stockUnits' },
        { text: 'Actions', value: 'action', sortable: false, width: 50 }
      ],
      editedStockPortfolio: {
        stockPositionId: 0,
        stockSymbol: '',
        stockName: '',
        stockPrice: '',
        stockUnits: ''
      },
      defaultStockPortfolioPosition: {
        stockPositionId: 0,
        stockSymbol: '',
        stockName: '',
        stockPrice: '',
        stockUnits: ''
      }
    }
  },
  computed: {
    ...mapState({
      portfolioPositions: state => state.portfolioPositions.portfolioPositions,
      user: state => state.account.user
    }),

    formTitle () {
      return this.editedStockPortfolio.stockPositionId === 0 ? 'New Position' : 'Edit Position'
    }
  },
  watch: {
    dialog (val) {
      val || this.close()
    }
  },
  methods: {
    // editPosition (portfolioPosition) {
    //   this.editedStockPortfolio = Object.assign({}, portfolioPosition)
    //   this.dialog = true
    //   window.location.reload()
    // },
    //
    // deletePosition (portfolioPosition) {
    //   console.log(portfolioPosition.portfolioPositionId)
    //   confirm('Are you sure you want to delete this position?') &&
    //   this.$store.dispatch('portfolioPositions/deletePortfolioPosition', { portfolioPositionId: portfolioPosition.portfolioPositionId })
    //   window.location.reload()
    // },

    close () {
      this.dialog = false
      this.editedStockPortfolio = Object.assign({}, this.defaultStockPortfolioPosition)
      this.$refs.form.reset()
    },

    savePosition () {
      const editedStockPortfolio = this.editedStockPortfolio
      this.loading = true
      console.log(editedStockPortfolio)
      if (editedStockPortfolio.portfolioPositionId === 0) {
        this.$store.dispatch('portfolioPositions/createPortfolioPosition', {
          portfolioPosition: editedStockPortfolio
        })
          .then(() => {
            this.close()
          })
          .finally(() => {
            window.location.reload()
            this.loading = false
          })
      } else {
        // this.$store.dispatch('portfolioPositions/modifyPortfolioPosition', {
        //   portfolioPosition: editedStockPortfolio
        // })
        //   .then(() => {
        //     this.close()
        //   })
        //   .finally(() => {
        //     window.location.reload()
        //     this.loading = false
        //   })
        window.location.reload()
      }
    }
  }
}
</script>

<style>
</style>
