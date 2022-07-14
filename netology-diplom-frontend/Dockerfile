FROM node:10
EXPOSE 8081
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
ENTRYPOINT ["npm", "run", "serve"]