module.exports = {
  content: ["./src/main/resources/**/*.{html,js}"],
  safelist: [
    'underline'
  ],
  theme: {
    extend: {
    fontFamily: {
        'display' : ['Nunito', 'sans-serif'],
        'sans' : ['Work Sans', 'sans-serif']
      }
    },
    container: {
      center: true,
    }
  },
  plugins: [
    require('@tailwindcss/forms')
  ]
}
