import * as types from '../mutation-types'

const state = {
	allBooks: [],
}

const mutations = {
	[types.SET_ALL_BOOKS] (state, bookList) {
		state.allBooks = bookList;
	}
}

const actions = {
	
}

export default {
	state,
	mutations, 
	actions
}