import {
    RESET_PASSWORD_ERROR_PASSWORD_TOO_SHORT,
    RESET_PASSWORD_ERROR_USERNAME_EMPTY,
    RESET_PASSWORD_PASSWORDS_MATCH,
    RESET_PASSWORD_TOKEN_SENT,
    RESET_PASSWORD_TOKEN_VALID
} from '../actions';
import {loginSuccess} from '../auth/actions';
import {guardedFetch} from '../helpers';

export function resetPassword(username, password1, password2, hash) {
    return function (dispatch) {
        dispatch({
            type: RESET_PASSWORD_ERROR_PASSWORD_TOO_SHORT,
            value: false
        });
        dispatch({
            type: RESET_PASSWORD_PASSWORDS_MATCH,
            value: true
        });
        dispatch({
            type: RESET_PASSWORD_TOKEN_VALID,
            value: true
        });
        dispatch({
            type: RESET_PASSWORD_ERROR_USERNAME_EMPTY,
            value: false
        });

        if (username.trim() === '') {
            dispatch({
                type: RESET_PASSWORD_ERROR_USERNAME_EMPTY,
                value: true
            });
            return;
        }

        if (password1 !== password2) {
            dispatch({
                type: RESET_PASSWORD_PASSWORDS_MATCH,
                value: false
            });
            return;
        }

        guardedFetch(dispatch, fetch('/api/people/' + username + '/passwords/' + hash, {
            method: 'PUT',
            body: JSON.stringify({
                password1,
                password2
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            response.json().then((msg) => {
                switch (msg.result) {
                    case 'RESET_PASSWORD_ERROR_PASSWORD_TOO_SHORT':
                        dispatch({
                            type: RESET_PASSWORD_ERROR_PASSWORD_TOO_SHORT,
                            value: true
                        });
                        break;
                    case 'PASSWORDS_DONT_MATCH':
                        dispatch({
                            type: RESET_PASSWORD_PASSWORDS_MATCH,
                            value: false
                        });
                        break;
                    case 'INVALID_RESET_PASSWORD_TOKEN':
                        dispatch({
                            type: RESET_PASSWORD_TOKEN_VALID,
                            value: false,
                            username: username,
                        });
                        break;
                    case 'SUCCESS':
                        loginSuccess(username, msg.personId, msg.token, msg.roles)(dispatch);
                        break;
                }
            });
        }));
    };
}

export function sendResetPasswordToken(usernameOrId) {
    return function (dispatch) {
        guardedFetch(dispatch, fetch('/api/people/' + usernameOrId + '/passwords/resetTokens', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((/*response*/) => {
            dispatch({
                type: RESET_PASSWORD_TOKEN_SENT
            });
        }));
    };
}
