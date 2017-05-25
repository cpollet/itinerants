import {RECEIVE_ATTENDANCES} from '../actions';

const initialState = {
    events: []
};

export default function (state = initialState, action) {
    switch (action.type) {
        case RECEIVE_ATTENDANCES:
            return Object.assign({}, state, {
                events: action.attendances,
            });
    }

    return state;
}