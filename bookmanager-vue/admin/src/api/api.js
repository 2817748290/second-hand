import axios from 'axios';

let base = '';

// user
export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };

export const getUserList = params => { return axios.post(`/api/user/getSearchUser`, params); };

export const getUserListPage = params => { return axios.post(`/api/user/getSearchUser`, params); };

export const removeUser = params => { return axios.post(`api/user/deleteUser`, params); };

export const updateUser = params => { return axios.post(`api/user/updateUser`, params); };

export const addUser = params => { return axios.post(`/api/user/addUser`, params); };

//book
export const getInitBookList = params => { return axios.post(`/api/book/getInitBookList`, params); };

export const deleteBook = params => { return axios.post(`/api/book/deleteBook`, params); };

export const addBook = params => { return axios.post(`/api/book/addBook`, params); };

export const updateBook = params => { return axios.post(`/api/book/updateBook`, params); };

export const getBookInfoById = params => { return axios.post(`/api/book/getBookInfoById`, params); };

//type
export const getTypeList = params => { return axios.post(`/api/type/getTypeList`, params); };

/* order */
export const getOrderList = params => { return axios.post(`/api/order/getOrderList`, params); };

export const getOrderListPage = params => { return axios.post(`/api/order/getSearchOrder`, params); };

export const updateOrder = params => { return axios.post(`/api/order/updateOrder`, params); };

export const deleteOrder = params => { return axios.post(`/api/order/deleteOrder`, params); };
