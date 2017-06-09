import {ATTENDANCES_FETCH_SUCCESS} from '../actions';

const initialState = {
    events: []
};

export default function (state = initialState, action) {
    switch (action.type) {
        case ATTENDANCES_FETCH_SUCCESS:
            return Object.assign({}, state, {
                events: action.attendances,
            });
    }

    return state;
}