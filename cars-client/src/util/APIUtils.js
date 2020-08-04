import { API_BASE_URL, CAR_LIST_SIZE, STORE_LIST_SIZE, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response => 
            response.json().then(json => {
                if(!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
        );
};

// Auth
export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

// Users
export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}


export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getAllUsers() {
    return request({
        url: API_BASE_URL + "/users",
        method: 'GET'
    });
}

export function getOneUserByUsername(username) {
    return request({
        url: API_BASE_URL + "/users/" + username,
        method: 'GET'
    });
}

export function getAllUserCreatedCars(username, page, size) {
    page = page || 0;
    size = size || CAR_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/users/" + username + "/cars?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getAllUserCreatedCarsBySearch(username, data, page, size) {
    page = page || 0;
    size = size || CAR_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/users/" + username + "/cars/search/" + data + "?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getAllUserCreatedStores(username, page, size) {
    page = page || 0;
    size = size || STORE_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/users/" + username + "/stores?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

// Cars
export function getAllCars(page, size) {
    page = page || 0;
    size = size || CAR_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/cars?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function createCar(carData) {
    return request({
        url: API_BASE_URL + "/cars",
        method: 'POST',
        body: JSON.stringify(carData)
    });
}

export function getOneCarByCarId(carId) {
    return request({
        url: API_BASE_URL + "/cars/" + carId,
        method: 'GET'
    });
}

export function getAllCarsBySearch(data, page, size) {
    page = page || 0;
    size = size || CAR_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/cars/search/" + data + "?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getAllCarsByRank(period, distance, page, fuel, size) {
    page = page || 0;
    size = size || CAR_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/cars/rank/" + period + "/" + distance + "/" + fuel + "?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

// Stores
export function getAllStores(page, size) {
    page = page || 0;
    size = size || STORE_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/stores?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getOneStoreByStorId(storId) {
    return request({
        url: API_BASE_URL + "/stores/" + storId,
        method: 'GET'
    });
}
