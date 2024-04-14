FROM node:20.11.0-bullseye-slim as build
WORKDIR /project
RUN npm install -g @angular/cli@17
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
RUN ng build --output-path=dist/frontend

# Stage 2: Serve the app with NGINX
FROM nginx:alpine
COPY --from=build /project/dist/frontend/browser /usr/share/nginx/html
# Copy SSL certificates for the frontend
COPY NO_CHECKIN /etc/ssl

# Copy custom NGINX configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose ports for HTTP and HTTPS
EXPOSE 443

# Start NGINX in the foreground
CMD ["nginx", "-g", "daemon off;"]
