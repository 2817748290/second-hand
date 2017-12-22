import * as types from './mutation-types'
import axios from 'axios'

export const setHotBooks = ({commit}, requestURL) => {
	axios.get(requestURL).then(res=> {
		commit(types.SET_HOT_BOOKS, res.data);
	});
}

export const setDiscountBooks = ({commit}, requestURL) => {
	axios.get(requestURL).then(res=> {
		commit(types.SET_DISCOUNT_BOOKS, res.data);
	});
}

export const setNewBooks = ({commit}, requestURL) => {
	axios.get(requestURL).then(res=> {
		commit(types.SET_NEW_BOOKS, res.data);
	});
}

export const setAllBooks = ({commit}, requestURL) => {
	axios.post(requestURL).then(res=> {
		commit(types.SET_ALL_BOOKS, res.data.result);
	});
}

export const setCategoryList = ({commit}, requestURL) => {
	axios.post(requestURL).then(res=> {
		commit(types.SET_CATEGORY_LIST, res.data.result);
	});
}

export const addToCart = ({commit}, book) => {
	commit(types.ADD_TO_CART, book);
};  

export const deleteCartBook = ({commit}, book) => {
	commit(types.DELETE_CART_BOOK, book);
};

export const clearCart = ({commit}) => {
	commit(types.CLEAR_CART);
};