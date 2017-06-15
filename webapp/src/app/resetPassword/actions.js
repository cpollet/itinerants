import {RESET_PASSWORD_ERROR, RESET_PASSWORD_ERROR_RESET, RESET_PASSWORD_TOKEN_SENT} from '../actions';
import {loginSuccess} from '../auth/actions';
import {guardedFetch} from '../helpers';
import {appCodes, serverCodesMapping} from './constants';

export function resetPassword(username, password1, password2, hash) {
    return function (dispatch) {
        dispatch({
            type: RESET_PASSWORD_ERROR_RESET
        });

        if (username.trim() === '') {
            dispatch({
                type: RESET_PASSWORD_ERROR,
                code: appCodes.usernameEmpty,
            });
            return;
        }

        if (password1 !== password2) {
            dispatch({
                type: RESET_PASSWORD_ERROR,
                code: appCodes.passwordsDontMatch,
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
                    case 'SUCCESS':
                        loginSuccess(username, msg.personId, msg.token, msg.roles)(dispatch);
                        break;

                    default:
                        dispatch({
                            type: RESET_PASSWORD_ERROR,
                            code: serverCodesMapping[msg.result],
                        });
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
