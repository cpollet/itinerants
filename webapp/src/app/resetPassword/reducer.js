import {PASSWORD_TOO_SHORT, PASSWORDS_MATCH, TOKEN_VALID, USERNAME_EMPTY} from '../actions';

const initialState = {
    passwordsMatch: true,
    passwordTooShort: false,
    tokenValid: true,
    usernameEmpty: false,
};

export default function (state = initialState, action) {
    switch (action.type) {
        case PASSWORDS_MATCH:
            return Object.assign({}, state, {
                passwordsMatch: action.value,
                passwordTooShort: false,
                tokenValid: true,
                usernameEmpty: false,
            });

        case PASSWORD_TOO_SHORT:
            return Object.assign({}, state, {
                passwordsMatch: true,
                passwordTooShort: action.value,
                tokenValid: true,
                usernameEmpty: false,
            });

        case TOKEN_VALID:
            return Object.assign({}, state, {
                passwordsMatch: true,
                passwordTooShort: false,
                tokenValid: action.value,
                usernameEmpty: false,
            });

        case USERNAME_EMPTY:
            return Object.assign({}, state, {
                passwordsMatch: true,
                passwordTooShort: false,
                tokenValid: true,
                usernameEmpty: action.value,
            });
    }

    return state;
}