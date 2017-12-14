import axios from 'axios';

let base = '';

// user
export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };

export const getUserList = params => { return axios.post(`/api/user/getSearchUser`, params); };

export const getUserListPage = params => { return axios.post(`/api/user/getSearchUser`, params); };

export const removeUser = params => { return axios.post(`api/user/deleteUser`, params); };

export const editUser = params => { return axios.post(`api/user/updateUser`, params); };

export const addUser = params => { return axios.post(`/api/user/addUser`, params); };

//book
export const getInitBookList = params => { return axios.post(`/api/book/getInitBookList`, params); };
