import {
    PROFILE_FETCH_ERROR,
    PROFILE_SAVE_ERROR_VALIDATION,
    PROFILE_FETCH,
    PROFILE_FETCH_SUCCESS,
    RESET_PASSWORD_TOKEN_SENT,
    PROFILE_SAVE_START,
    PROFILE_SAVE_SUCCESS
} from '../../actions';

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
    errors: {},
};

export default function (state = initialState, action) {
    switch (action.type) {
        case PROFILE_FETCH:
            return initialState;

        case PROFILE_FETCH_SUCCESS:
            return Object.assign({}, state, {
                data: {
                    firstName: action.data.firstName,
                    lastName: action.data.lastName,
                    email: action.data.email,
                },
                ready: true,
            });

        case PROFILE_SAVE_START:
            return Object.assign({}, state, {
                saving: true,
                errors: {},
            });

        case PROFILE_SAVE_SUCCESS:
            return Object.assign({}, state, {
                saving: false,
            });

        case RESET_PASSWORD_TOKEN_SENT:
            return Object.assign({}, state, {
                resetPasswordTokenSent: true,
            });

        case PROFILE_FETCH_ERROR:
            return Object.assign({}, initialState, {
                ready: true,
                saving: false,
            });
        case PROFILE_SAVE_ERROR_VALIDATION: {
            let errors = {};

            action.errors.forEach((e) => {
                errors = Object.assign({}, errors, {
                    [e.field]: Array.concat(errors[e.field] || [], e.message)
                });
            });

            return Object.assign({}, state, {
                saving: false,
                errors: errors,
            });
        }
    }

    return state;
}