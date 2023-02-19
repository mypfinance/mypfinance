import PortfolioTrackerService from '../../services/portfolio-tracker-service.js'
import { ADD_ALERT } from '@/store/_actiontypes'

const state = {
  portfolioPositions: []
}

const actions = {
  logout ({ commit }) {
    commit('logout')
  },
  loadPortfolioPositions ({ commit }) {
    PortfolioTrackerService.getAlLPositions().then(portfolioPositions => {
      commit('getAllPortfolioPositions', portfolioPositions)
    })
  },
  createPortfolioPosition ({ commit, dispatch }, portfolioPosition) {
    return PortfolioTrackerService.createStockPosition(portfolioPosition).then(portfolioPosition => {
      commit('createPortfolioPosition', portfolioPosition)
      dispatch(`alert/${ADD_ALERT}`, { message: 'Position added successfully', color: 'success' }, { root: true })
    })
  },
  // modifyportfolioPosition ({ commit, dispatch }, portfolioPosition) {
  //   return PortfolioTrackerService.modifyPortfolioPosition(portfolioPosition).then(portfolioPosition => {
  //     commit('modifyportfolioPosition', portfolioPosition)
  //     dispatch(`alert/${ADD_ALERT}`, { message: 'Position updated successfully', color: 'success' }, { root: true })
  //   })
  // },
  // deleteportfolioPosition ({ commit, dispatch }, portfolioPositionId) {
  //   portfolioPositionsService.deleteportfolioPositionById(portfolioPositionId)
  //     .then(() => {
  //       const newportfolioPositions = state.portfolioPositions.filter(et => et.portfolioPositionId === portfolioPositionId)[0]
  //       commit('deleteportfolioPosition', portfolioPositionId)
  //       dispatch(`alert/${ADD_ALERT}`, { message: 'Expense Transaction deleted successfully', color: 'success' }, { root: true })
  //       dispatch(`statistics/${EDIT_STATISTICS}`, { expense: newportfolioPositions, operation: 'remove' }, { root: true })
  //     })
  // },
  // deleteportfolioPositionsByCategory ({ commit, dispatch }, categoryName) {
  //   portfolioPositionsService.deleteportfolioPositionByCategory(categoryName)
  //     .then(() => {
  //       const newportfolioPositions = state.portfolioPositions.filter(et => et.categoryName === categoryName)[0]
  //       commit('deleteAllportfolioPositionByCategory', categoryName)
  //       dispatch(`alert/${ADD_ALERT}`, { message: 'Expense Transactions have been deleted successfully', color: 'success' }, { root: true })
  //       dispatch(`statistics/${EDIT_STATISTICS}`, { expense: newportfolioPositions, operation: 'remove' }, { root: true })
  //     })
  // }
}

const mutations = {
  getAllPortfolioPositions (state, portfolioPositions) {
    state.portfolioPositions = portfolioPositions
  },
  createPortfolioPosition (state, portfolioPosition) {
    state.portfolioPositions.push(portfolioPosition)
  },
  // modifyportfolioPosition (state, portfolioPositionId, portfolioPosition) {
  //   const portfolioPositionUpdated = state.portfolioPositions.find(et => et.portfolioPositionId === portfolioPositionId)
  //   portfolioPositionUpdated.name = portfolioPosition.name
  //   portfolioPositionUpdated.description = portfolioPosition.description
  //   portfolioPositionUpdated.budget = portfolioPosition.budget
  //   portfolioPositionUpdated.colourHex = portfolioPosition.colourHex
  // },
  // deleteportfolioPosition (state, portfolioPositionId) {
  //   state.portfolioPositions = state.portfolioPositions.filter(et => et.portfolioPositionId !== portfolioPositionId)
  // },
  // deleteAllportfolioPositionByCategory (state, categoryName) {
  //   state.portfolioPositions = state.portfolioPositions.filter(et => et.categoryName !== categoryName)
  // },
  logout (state) {
    state.portfolioPositions = []
  }
}

export const portfolioPositions = {
  namespaced: true,
  state,
  actions,
  mutations
}
