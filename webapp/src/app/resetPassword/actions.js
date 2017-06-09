import {PASSWORD_TOO_SHORT, PASSWORDS_MATCH, RESET_PASSWORD_TOKEN_SENT, TOKEN_VALID, USERNAME_EMPTY} from '../actions';
import {loginSuccess} from '../auth/actions';

export function resetPassword(username, password1, password2, hash) {
    return function (dispatch) {
        dispatch({
            type: PASSWORD_TOO_SHORT,
            value: false
        });
        dispatch({
            type: PASSWORDS_MATCH,
            value: true
        });
        dispatch({
            type: TOKEN_VALID,
            value: true
        });
        dispatch({
            type: USERNAME_EMPTY,
            value: false
        });

        if (username.trim() === '') {
            dispatch({
                type: USERNAME_EMPTY,
                value: true
            });
            return;
        }

        if (password1 !== password2) {
            dispatch({
                type: PASSWORDS_MATCH,
                value: false
            });
            return;
        }

        fetch('/api/people/' + username + '/passwords/' + hash, {
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
                    case 'PASSWORD_TOO_SHORT':
                        dispatch({
                            type: PASSWORD_TOO_SHORT,
                            value: true
                        });
                        break;
                    case 'PASSWORDS_DONT_MATCH':
                        dispatch({
                            type: PASSWORDS_MATCH,
                            value: false
                        });
                        break;
                    case 'INVALID_RESET_PASSWORD_TOKEN':
                        dispatch({
                            type: TOKEN_VALID,
                            value: false,
                            username: username,
                        });
                        break;
                    case 'SUCCESS':
                        loginSuccess(username, msg.personId, msg.token, msg.roles)(dispatch);
                        break;
                }
            });
        }).catch((/* ex */) => {
            console.log('fatal error');
        });
    };
}

export function sendResetPasswordToken(usernameOrId) {
    return function (dispatch) {
        fetch('/api/people/' + usernameOrId + '/passwords/resetTokens', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((/*response*/) => {
            dispatch({
                type: RESET_PASSWORD_TOKEN_SENT
            });
        }).catch((/* ex */) => {
            console.log('fatal error');
        });
    };
}
