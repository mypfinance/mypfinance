module.exports = {
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_BASE_URL,
        ws: true,
        changeOrigin: true
      }
    }
  },

  transpileDependencies: [
    'vuetify'
  ]
}
