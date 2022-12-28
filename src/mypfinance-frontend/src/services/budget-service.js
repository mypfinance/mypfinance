import axios from 'axios'
import authHeader from '@/services/auth-header'

var headers = {
  withCredentials: false,
  headers: {
    Authorization: authHeader(),
    'Content-Type': 'application/json'
  }
}

class BudgetService {
  getUserCurrentBudget (currentPage, perPage) {
    return axios.get('/api/user/current/budget', headers
    ).then(response => {
      return response.data
    })
  }
}

export default new BudgetService()
