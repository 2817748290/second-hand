import * as types from '../mutation-types'

const state = {
	categoryList: []
}

const mutations = {
	[types.SET_CATEGORY_LIST] (state, categoryList) {
		state.categoryList = categoryList;
	}
}

const actions = {
	
}

export default {
	state,
	mutations, 
	actions
}