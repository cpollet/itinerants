import {FETCH_PROFILE, RECEIVED_PROFILE, RESET_PASSWORD_TOKEN_SENT, SAVE_PROFILE, SAVED_PROFILE} from '../../actions';

const initialState = {
    data: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
    },
    ready: false,
    saving: false,
    resetPasswordTokenSent: false,
};

export default function (state = initialState, action) {
    switch (action.type) {
        case FETCH_PROFILE:
            return initialState;

        case RECEIVED_PROFILE:
            return Object.assign({}, state, {
                data: {
                    firstName: action.data.firstName,
                    lastName: action.data.lastName,
                    email: action.data.email,
                },
                ready: true,
            });

        case SAVE_PROFILE:
            return Object.assign({}, state, {
                saving: true,
            });

        case SAVED_PROFILE:
            return Object.assign({}, state, {
                saving: false,
            });

        case RESET_PASSWORD_TOKEN_SENT:
            return Object.assign({}, state, {
                resetPasswordTokenSent: true,
            });
    }

    return state;
}