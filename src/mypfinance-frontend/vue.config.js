module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'https://mypfinance.herokuapp.com',
        ws: true,
        changeOrigin: true
      }
    }
  },

  transpileDependencies: [
    'vuetify'
  ]
}
