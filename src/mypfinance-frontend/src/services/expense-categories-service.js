import axios from 'axios'
import authHeader from '@/services/auth-header'

const headers = {
  withCredentials: true,
  headers: {
    Authorization: authHeader(),
    'Content-Type': 'application/json'
  }
}

class ExpenseCategoriesService {
  getAllExpenseCategories () {
    return axios.get('/api/expense/categories', headers
    ).then(response => {
      return response.data
    })
  }

  createExpenseCategory (expenseCategory) {
    const requestCategory = {
      categoryName: expenseCategory.expenseCategory.categoryName,
      color: expenseCategory.expenseCategory.color
    }

    return axios.post('/api/add/expense/category', requestCategory, headers
    ).then(response => {
      return response.data
    })
  }

  modifyExpenseCategory (modifiedCategory) {
    const requestCategory = {
      categoryName: modifiedCategory.expenseCategory.categoryName,
      color: modifiedCategory.expenseCategory.color
    }
    const expenseCategoryId = modifiedCategory.expenseCategory.expenseCategoryId

    return axios.put( '/api/modify/expense/category/' + expenseCategoryId, requestCategory, headers
    ).then(response => {
      return response.data
    })
  }

  deleteExpenseCategory (expenseCategoryId) {
    const id = expenseCategoryId.expenseCategoryId

    return axios.delete('/api/delete/expense/category/' + id, headers
    ).then(response => {
      return response.data
    })
  }
}

export default new ExpenseCategoriesService()
