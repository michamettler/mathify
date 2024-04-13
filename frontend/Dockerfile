# Stage 1: Build the Angular project
FROM node:20.11.0-bullseye-slim as build

# Set working directory
WORKDIR /project

# Install Angular CLI
RUN npm install -g @angular/cli@17

# Copy project files into the Docker image
COPY package.json package-lock.json ./
RUN npm ci
COPY . .

# Build the project for production
RUN ng build --output-path=dist/frontend  # Ensure this matches the COPY path below

# Stage 2: Serve the app with NGINX
FROM nginx:alpine
COPY --from=build /project/dist/frontend/browser /usr/share/nginx/html

# Copy SSL certificates for the frontend
COPY NO_CHECKIN /etc/ssl

# Copy custom NGINX configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose ports for HTTP and HTTPS
EXPOSE 443 80

# Start NGINX in the foreground
CMD ["nginx", "-g", "daemon off;"]
