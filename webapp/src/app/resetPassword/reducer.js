import {RESET_PASSWORD_ERROR, RESET_PASSWORD_ERROR_RESET, RESET_PASSWORD_TOKEN_SENT} from '../actions';
import {appCodes} from './constants';
const initialState = {
    passwordsMatch: true,
    passwordTooShort: false,
    tokenValid: true,
    usernameEmpty: false,
    username: null,
    resetPasswordTokenSent: false,
};

export default function (state = initialState, action) {
    switch (action.type) {
        case RESET_PASSWORD_ERROR:
            switch (action.code) {
                case appCodes.passwordTooShort:
                    return Object.assign({}, state, {
                        passwordTooShort: true,
                    });

                case appCodes.usernameEmpty:
                    return Object.assign({}, state, {
                        usernameEmpty: true,
                    });

                case appCodes.passwordsDontMatch:
                    return Object.assign({}, initialState, {
                        passwordsMatch: false,
                    });

                case appCodes.invalidToken:
                    return Object.assign({}, initialState, {
                        tokenValid: false,
                        username: action.username,
                    });
            }
            console.log('Error: invalid error code: ' + action.code);
            return state;

        case RESET_PASSWORD_ERROR_RESET:
            return initialState;

        case RESET_PASSWORD_TOKEN_SENT:
            return Object.assign({}, initialState, {
                resetPasswordTokenSent: true,
            });
    }

    return state;
}