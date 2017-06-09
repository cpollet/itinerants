import {RESET_PASSWORD_ERROR_PASSWORD_TOO_SHORT, RESET_PASSWORD_PASSWORDS_MATCH, RESET_PASSWORD_TOKEN_SENT, RESET_PASSWORD_TOKEN_VALID, RESET_PASSWORD_ERROR_USERNAME_EMPTY} from '../actions';

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
        case RESET_PASSWORD_PASSWORDS_MATCH:
            return Object.assign({}, initialState, {
                passwordsMatch: action.value,
            });

        case RESET_PASSWORD_ERROR_PASSWORD_TOO_SHORT:
            return Object.assign({}, initialState, {
                passwordTooShort: action.value,
            });

        case RESET_PASSWORD_TOKEN_VALID:
            return Object.assign({}, initialState, {
                tokenValid: action.value,
                username: action.username,
            });

        case RESET_PASSWORD_ERROR_USERNAME_EMPTY:
            return Object.assign({}, initialState, {
                usernameEmpty: action.value,
            });

        case RESET_PASSWORD_TOKEN_SENT:
            return Object.assign({}, initialState, {
                resetPasswordTokenSent: true,
            });
    }

    return state;
}