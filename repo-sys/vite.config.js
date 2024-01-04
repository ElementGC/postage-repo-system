import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  //跨域处理
  server: {
    proxy: {
      '/api':{ //获取路径中包含了/api 的请求
        target: 'http://localhost:8888', //后端服务器地址
        changeOrigin: true,
        rewrite: (path)=>path.replace(/^\/api/,'')  //将请求中的/api替换为''

      }
    }
  }
})
