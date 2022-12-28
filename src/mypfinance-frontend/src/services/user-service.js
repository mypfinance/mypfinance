import axios from 'axios'
import authHeader from './auth-header'

const headers = {
  withCredentials: true,
  headers: {
    Authorization: authHeader(),
    'Content-Type': 'application/json'
  }
}

class UserService {
  getUserInfo () {
    return axios.get( '/api/user', headers)
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
    return axios.put('/api/user/modify', requestUser, headers
    ).then(response => {
      return response.data
    })
  }

  deleteUserFromApp () {
    return axios.delete('/api/user/delete', headers
    ).then(response => {
      return response.data
    })
  }
}

export default new UserService()