import {PASSWORD_TOO_SHORT, PASSWORDS_MATCH, RESET_PASSWORD_TOKEN_SENT, TOKEN_VALID, USERNAME_EMPTY} from '../actions';

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
        case PASSWORDS_MATCH:
            return Object.assign({}, initialState, {
                passwordsMatch: action.value,
            });

        case PASSWORD_TOO_SHORT:
            return Object.assign({}, initialState, {
                passwordTooShort: action.value,
            });

        case TOKEN_VALID:
            return Object.assign({}, initialState, {
                tokenValid: action.value,
                username: action.username,
            });

        case USERNAME_EMPTY:
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