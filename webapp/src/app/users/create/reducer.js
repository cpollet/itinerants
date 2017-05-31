import {CREATE_USER_ERROR, CREATE_USER_SUCCESS} from '../../actions';

const initialState = {};

export default function (state = initialState, action) {
    switch (action.type) {
        case CREATE_USER_ERROR:
            return Object.assign({}, state, {
                error: true,
            });
        case CREATE_USER_SUCCESS:
            return Object.assign({}, state, {
                error: false,
            });
    }

    return state;
}
