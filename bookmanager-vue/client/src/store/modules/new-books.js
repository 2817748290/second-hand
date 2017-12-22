import * as types from '../mutation-types'

const state = {
	newBooks: [],
}

const mutations = {
	[types.SET_NEW_BOOKS] (state, books) {
		state.newBooks = books.bookList;
	}
}

const actions = {
	
}

export default {
	state,
	mutations, 
	actions
}