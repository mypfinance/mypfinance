import axios from 'axios'
import authHeader from '@/services/auth-header'

const API_URL = process.env.VUE_APP_BASE_URL

var headers = {
  headers: {
    Authorization: authHeader()
  }
}

class PortfolioTrackerService {
  getCurrentStockInfo (stockSymbol) {
    const request = {
      stockSymbol: stockSymbol
    }

    return axios.get(API_URL + '/api/current/info', request, headers
    ).then(response => {
      return response.data.stock
    })
  }

  getAlLPositions (currentPage, perPage) {
    return axios.get(API_URL + '/api/positions', headers
    ).then(response => {
      return response.data
    })
  }

  createStockPosition (position) {
    const requestStock = {
      stockSymbol: position.stockSymbol,
      stockName: position.stockName,
      stockPrice: Number(position.stockPrice),
      stockUnits: Number(position.stockUnits)
    }

    return axios.post(API_URL + '/api/stock', requestStock, headers)
      .then(response => {
        return response.data
      })
  }

  // modifyExpenseTransaction (transaction) {
  //   const requestTransaction = {
  //     date: transaction.expenseTransaction.date,
  //     expenseAmount: Number(transaction.expenseTransaction.expenseAmount),
  //     categoryName: transaction.expenseTransaction.categoryName,
  //     description: transaction.expenseTransaction.description
  //   }
  //
  //   const expenseTransactionId = transaction.expenseTransaction.expenseTransactionId
  //
  //   return axios.put(API_URL + '/api/modify/expense/transaction/' + expenseTransactionId, requestTransaction, headers)
  //     .then(response => {
  //       return response.data
  //     })
  // }

  // deleteExpenseTransactionByCategory (categoryName) {
  //   const expenseCategoryName = categoryName.categoryName
  //
  //   return axios.delete(API_URL + '/api/delete/expense/transactions/category?categoryName=' + expenseCategoryName, headers)
  // }
  //
  // deleteExpenseTransactionById (id) {
  //   const expenseTransactionId = id.expenseTransactionId
  //
  //   return axios.delete(API_URL + '/api/delete/expense/transaction/' + expenseTransactionId, headers).then(response => {
  //     return response.data
  //   })
  // }
}

export default new PortfolioTrackerService()
