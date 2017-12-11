import axios from 'axios';

let base = '';

export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };

// export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };
export const getUserList = params => { return axios.post(`/api/user/getSearchUser`, params); };

export const getUserListPage = params => { return axios.post(`/api/user/getSearchUser`, params); };

export const removeUser = params => { return axios.post(`api/user/deleteUser`, params); };

export const editUser = params => { return axios.post(`api/user/updateUser`, params); };

export const addUser = params => { return axios.post(`/api/user/addUser`, params); };