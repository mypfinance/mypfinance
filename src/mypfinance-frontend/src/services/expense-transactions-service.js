import axios from 'axios'
import authHeader from '@/services/auth-header'

var headers = {
  withCredentials: true,
  headers: {
    Authorization: authHeader(),
    'Content-Type': 'application/json',
  }
}

class ExpenseTransactionsService {
  getAllExpenseTransactions (currentPage, perPage) {
    return axios.get('/api/expense/transactions', headers
    ).then(response => {
      return response.data.transactions
    })
  }

  getTransactionById (id) {
    return axios.get('/api/expense/transaction/' + id, headers
    ).then(response => {
      return response.data
    })
  }

  getTransactionsByDate (date) {
    return axios.get('/api/expense/transactions/date?date=' + date, headers
    ).then(response => {
      return response.data
    })
  }

  getTransactionsForCurrentMonth (year, month) {
    return axios.get('/api/expense/transactions/current/' + year + '/' + month, headers
    ).then(response => {
      return response.data
    })
  }

  getTransactionsForCurrentMonthByCategory (year, month) {
    return axios.get('/api/expense/transactions/year/' + year + '/' + month, headers
    ).then(response => {
      return response.data
    })
  }

  getTransactionByCategory (category) {
    return axios.get('/api/expense/transactions/category?category' + category, headers
    ).then(response => {
      return response.data
    })
  }

  createExpenseTransaction (transaction) {
    const requestTransaction = {
      date: transaction.expenseTransaction.date,
      expenseAmount: Number(transaction.expenseTransaction.expenseAmount),
      categoryName: transaction.expenseTransaction.categoryName,
      description: transaction.expenseTransaction.description
    }

    return axios.post('/api/add/expense/transaction', requestTransaction, headers)
      .then(response => {
        return response.data
      })
  }

  modifyExpenseTransaction (transaction) {
    const requestTransaction = {
      date: transaction.expenseTransaction.date,
      expenseAmount: Number(transaction.expenseTransaction.expenseAmount),
      categoryName: transaction.expenseTransaction.categoryName,
      description: transaction.expenseTransaction.description
    }

    const expenseTransactionId = transaction.expenseTransaction.expenseTransactionId

    return axios.put( '/api/modify/expense/transaction/' + expenseTransactionId, requestTransaction, headers)
      .then(response => {
        return response.data
      })
  }

  deleteExpenseTransactionByCategory (categoryName) {
    const expenseCategoryName = categoryName.categoryName

    return axios.delete('/api/delete/expense/transactions/category?categoryName=' + expenseCategoryName, headers)
  }

  deleteExpenseTransactionById (id) {
    const expenseTransactionId = id.expenseTransactionId

    return axios.delete('/api/delete/expense/transaction/' + expenseTransactionId, headers).then(response => {
      return response.data
    })
  }
}

export default new ExpenseTransactionsService()
