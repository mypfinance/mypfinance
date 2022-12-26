import axios from 'axios'
import authHeader from '@/services/auth-header'

const API_URL = 'https://mypfinance.herokuapp.com/'
const headers = {
  withCredentials: true,
  headers: {
    Authorization: authHeader(),
    'Content-Type': 'application/json'
  }
}

class IncomeCategoriesService {
  getAllIncomeCategories () {
    return axios.get(API_URL + 'api/income/categories', headers
    ).then(response => {
      return response.data
    })
  }

  createIncomeCategory (incomeCategory) {
    const requestCategory = {
      categoryName: incomeCategory.incomeCategory.categoryName,
      color: incomeCategory.incomeCategory.color
    }

    return axios.post(API_URL + 'api/add/income/category', requestCategory, headers
    ).then(response => {
      return response.data
    })
  }

  modifyIncomeCategory (modifiedCategory) {
    const requestCategory = {
      categoryName: modifiedCategory.incomeCategory.categoryName,
      color: modifiedCategory.incomeCategory.color
    }
    const incomeCategoryId = modifiedCategory.incomeCategory.incomeCategoryId

    return axios.put(API_URL + 'api/modify/income/category/' + incomeCategoryId, requestCategory, headers
    ).then(response => {
      return response.data
    })
  }

  deleteIncomeCategory (incomeCategoryId) {
    const id = incomeCategoryId.incomeCategoryId

    return axios.delete(API_URL + 'api/delete/income/category/' + id, headers
    ).then(response => {
      return response.data
    })
  }
}

export default new IncomeCategoriesService()
