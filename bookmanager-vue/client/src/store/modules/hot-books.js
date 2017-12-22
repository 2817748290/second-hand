import * as types from '../mutation-types'

const state = {
	hotBooks: [],
}

const mutations = {
	[types.SET_HOT_BOOKS] (state, books) {
		state.hotBooks = books.bookList;
	}
}

const actions = {
	
}

export default {
	state,
	mutations, 
	actions
}