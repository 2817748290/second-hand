import Vue from 'vue'
import Vuex from 'vuex'
import * as getters from './getters'
import * as actions from './actions'
import hotBooks from './modules/hot-books'
import discountBooks from './modules/discount-books'
import newBooks from './modules/new-books'
import allBooks from './modules/all-books'
import cart from './modules/cart'
import categoryList from './modules/category-list'

Vue.use(Vuex)

export default new Vuex.Store({
	strict: true,
	getters,
	actions,
	modules: {
		hotBooks,
		discountBooks,
		newBooks,
		allBooks,
		cart,
		categoryList
	}
})