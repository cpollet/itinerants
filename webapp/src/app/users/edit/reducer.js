import {
    FAIL_PROFILE,
    FAIL_VALIDATION_PROFILE,
    FETCH_PROFILE,
    RECEIVED_PROFILE,
    RESET_PASSWORD_TOKEN_SENT,
    SAVE_PROFILE,
    SAVED_PROFILE
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
                errors: {},
            });

        case SAVED_PROFILE:
            return Object.assign({}, state, {
                saving: false,
            });

        case RESET_PASSWORD_TOKEN_SENT:
            return Object.assign({}, state, {
                resetPasswordTokenSent: true,
            });

        case FAIL_PROFILE:
            return Object.assign({}, initialState, {
                ready: true,
                saving: false,
            });
        case FAIL_VALIDATION_PROFILE: {
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