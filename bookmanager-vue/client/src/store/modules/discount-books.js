import * as types from '../mutation-types'

const state = {
	discountBooks: [],
}

const mutations = {
	[types.SET_DISCOUNT_BOOKS] (state, books) {
		state.discountBooks = books.bookList;
	}
}

const actions = {
	
}

export default {
	state,
	mutations, 
	actions
}