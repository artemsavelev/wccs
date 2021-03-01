module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/wccs/' : '/',
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