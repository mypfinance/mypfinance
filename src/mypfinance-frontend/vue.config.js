module.exports = {
  devServer: {
    port: 3000,
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
