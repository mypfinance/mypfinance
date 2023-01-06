import axios from 'axios'
import authHeader from './auth-header'

const API_URL = process.env.VUE_APP_BASE_URL

const headers = {
  headers: {
    Authorization: authHeader()
  }
}

class UserService {
  getUserInfo () {
    return axios.get( API_URL + '/api/user', headers)
      .then(response => {
        return response.data
      })
  }

  modifyUserInformation (user) {
    const requestUser = {
      username: user.username,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      currentBudget: user.currentBudget
    }
    return axios.put(API_URL + '/api/user/modify', requestUser, headers
    ).then(response => {
      return response.data
    })
  }

  deleteUserFromApp () {
    return axios.delete(API_URL + '/api/user/delete', headers
    ).then(response => {
      return response.data
    })
  }
}

export default new UserService()
