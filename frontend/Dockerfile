FROM node:20.11.0-bullseye-slim

RUN mkdir /project
WORKDIR /project

RUN npm install -g @angular/cli@17

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0"]
