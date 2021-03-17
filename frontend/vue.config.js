module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/wccs' : '/',
  outputDir: '../src/main/resources/static/',
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