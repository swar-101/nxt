# 1. Install Node.js
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt install -y nodejs
sudo apt update

# 2. Create a React app (no global install)
npx create-react-app nxt
cd nxt

# 3. Install additional dependencies (if needed)
npm install --legacy-peer-deps  # Optional


npm install -D tailwindcss postcss autoprefixer

npx tailwindcss init -p




rm -rf node_modules package-lock.json
