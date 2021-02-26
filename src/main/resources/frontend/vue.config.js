module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/frontend/dist/' : '/',
  "transpileDependencies": [
    "vuetify"
  ],
  css: {
    loaderOptions: {
      scss: {
        prependData: `@import "@/assets/styles/styles.scss";`
      }
    }
  }
}