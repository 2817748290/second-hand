import axios from 'axios';

// user
export const requestLogin = params => { return axios.post(`/api/login/userLogin`, params).then(res => res.data); };

//book
export const getSearchBook = params => { return axios.post(`/api/book/getInitBookList`, params)};

export const getBookList = params => { return axios.post(`/api/book/getBookList`, params)};

export const getBookInfoById = params => { return axios.post(`/api/book/getBookInfoById`, params)};

//type
export const getTypeList = params => { return axios.post(`/api/type/getTypeList`, params)};
