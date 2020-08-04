## Build the react application
FROM node:14.5.0-alpine as build

WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy main application
COPY . ./

# Arguments
ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

# Build application
RUN npm run build

## Serve the React application from Nginx
FROM nginx:1.17.0-alpine

# Copy the react build
COPY --from=build /app/build /var/www

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
