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
        colors: {
            'mint': '#a8e6cf',
            'peach': '#ffd3a5',
            'purple': '#e8b4ff',
        },
        fontFamily: {
            'inter': ['Inter', '-apple-system', 'BlinkMacSystemFont', 'sans-serif'],
        },
        animation: {
            'float': 'float 8s ease-in-out infinite',
            'fadeInUp': 'fadeInUp 0.6s ease forwards'
        },
        keyframes: {
            float: {
                '0%, 100%': { transform: 'translateX(-50px)', opacity: '0.1' },
                '50%': { transform: 'translateX(50px)', opacity: '0.3' }
            },
            fadeInUp: {
                'from': { opacity: '0', transform: 'translateY(30px)' },
                'to': { opacity: '1', transform: 'translateY(0)' }
            }
        }
    }
  },
  plugins: [],
}