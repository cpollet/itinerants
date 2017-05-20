import {LOGIN_SUCCESS, PASSWORD_TOO_SHORT, PASSWORDS_MATCH, TOKEN_VALID, USERNAME_EMPTY} from '../actions';

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
                            value: false
                        });
                        break;
                    case 'SUCCESS':
                        dispatch({
                            type: LOGIN_SUCCESS,
                            username: username,
                            personId: msg.personId,
                            token: msg.token,
                            roles: msg.roles
                        });
                        break;
                }
            });
        }).catch((/* ex */) => {
            console.log('fatal error');
            // dispatch({
            //     type: LOGIN_ERROR
            // });
        });
    };

}