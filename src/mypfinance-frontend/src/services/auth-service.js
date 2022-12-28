import axios from 'axios'

class AuthService {
  login (loginForm) {
    return axios
      .get('/api/login', {
        params: {
          username: loginForm.username,
          password: loginForm.password
        }
      }).then(response => {
        localStorage.setItem('user', JSON.stringify(response.data))
        console.log(response.data)
        return response.data
      }, (error) => {
        console.log(error)
      })
  }

  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post('/api/register', {
      username: user.username,
      password: user.password,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      currentBudget: user.currentBudget
    })
  }
}

export default new AuthService()
