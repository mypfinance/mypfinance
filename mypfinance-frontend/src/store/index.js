import Vue from 'vue'
import Vuex from 'vuex'
import { alert } from '@/store/modules/alert'
import { loader } from '@/store/modules/loader'
import { account } from '@/store/modules/account'
import { expenseCategories } from '@/store/modules/expensecategories'
import createPersistedState from 'vuex-persistedstate'
import SecureLS from 'secure-ls'
import { incomeCategories } from '@/store/modules/incomecategories'
import { expenseTransactions } from '@/store/modules/expensetransactions'
import { incomeTransactions } from '@/store/modules/incometransactions'
const ls = new SecureLS({ isCompression: false })

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    alert,
    loader,
    account,
    expenseCategories,
    incomeCategories,
    expenseTransactions,
    incomeTransactions
  },
  plugins: [
    createPersistedState({
      storage: {
        getItem: key => ls.get(key),
        setItem: (key, value) => ls.set(key, value),
        removeItem: key => ls.remove(key)
      }
    })
  ]

})

export default store
