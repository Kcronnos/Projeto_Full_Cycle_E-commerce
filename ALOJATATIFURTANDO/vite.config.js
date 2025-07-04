import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import flowbiteReact from "flowbite-react/plugin/vite";
import tailwindcss from '@tailwindcss/vite';
import path from "path";

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), flowbiteReact(), tailwindcss()],
  resolve: {
    alias: {
      "@": path.resolve("./src"),
      "@Components": path.resolve("./src/Components"),
      "@Controllers": path.resolve("./src/Controllers"),
      "@Pages": path.resolve("./src/Pages"),
      "@Layouts": path.resolve("./src/Layouts"),
      "@Theme": path.resolve("./src/Theme"),
    },
  },
  server: {
    proxy: {
      '/tatifurtando': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
