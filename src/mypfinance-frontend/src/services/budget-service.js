import axios from 'axios'
import authHeader from '@/services/auth-header'

var headers = {
  headers: {
    Authorization: authHeader()
  }
}

class BudgetService {
  getUserCurrentBudget (currentPage, perPage) {
    return axios.get(process.env.VUE_APP_BASE_URL + '/api/user/current/budget', headers
    ).then(response => {
      return response.data
    })
  }
}

export default new BudgetService()
