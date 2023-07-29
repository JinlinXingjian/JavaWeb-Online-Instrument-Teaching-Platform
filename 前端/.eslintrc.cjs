/* eslint-env node */
module.exports = {
  root: true,
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended'
  ],
  ruls:{
    "vue/multi-word-component-names":"off"
  },
  parserOptions: {
    ecmaVersion: 'latest'
  }
}
