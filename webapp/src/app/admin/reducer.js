import {RECEIVE_PEOPLE} from '../actions';

const initialState = {
    people: [],
};

export default function (state = initialState, action) {
    switch (action.type) {
        case RECEIVE_PEOPLE:
            return Object.assign({}, state, {
                people: action.people,
            });
    }

    return state;
}
