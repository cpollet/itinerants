import {PEOPLE_FETCH_SUCCESS} from '../actions';

const initialState = {
    people: [],
};

export default function (state = initialState, action) {
    switch (action.type) {
        case PEOPLE_FETCH_SUCCESS:
            return Object.assign({}, state, {
                people: action.people,
            });
    }

    return state;
}
