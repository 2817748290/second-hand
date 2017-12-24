import * as types from '../mutation-types'

const state = {
	cartBooks: []
}

const actions = {

}

const mutations = {
	[types.ADD_TO_CART] (state, book) {
		let isStored = state.cartBooks.some(function(item, index, array) {
			return item.id === book.id;
		});
		if (!isStored) {
			state.cartBooks.push(book);
		}
	},
	[types.DELETE_CART_BOOK] (state, book) {
		let index = state.cartBooks.indexOf(book);
		state.cartBooks.splice(index, 1)
	},
	[types.CLEAR_CART] (state) {
		state.cartBooks = [];
	}
}

export default {
	state,
	mutations,
	actions
}