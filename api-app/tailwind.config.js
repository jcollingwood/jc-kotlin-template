/** @type {import('@tailwindcss/cli').Config} */
module.exports = {
  content: {
      relative: true,
      files: [
        './src/main/kotlin/**/*.kt'
      ]
  },
  theme: {
    extend: {
        fontFamily: {
            inter: ['Inter', 'sans-serif'],
        }
    },
  },
  plugins: [],
}