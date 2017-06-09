import {push} from 'react-router-redux';
import {authenticatedFetch, guardedFetch} from '../../helpers';
import {reset as resetForm} from '../../../lib/form/FormContainer';
import {CREATE_USER_ERROR, CREATE_USER_SUCCESS} from '../../actions';

export function create(data) {
    return function (dispatch, getState) {
        guardedFetch(dispatch, authenticatedFetch('/api/people', dispatch, getState(), {
            method: 'POST',
            body: JSON.stringify({
                firstName: data.firstName,
                lastName: data.lastName,
                username: data.username,
                email: data.email,
                targetRatio: data.targetRatio,
                roles: data.roles,
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.status !== 200) {
                dispatch({
                    type: CREATE_USER_ERROR,
                });
                return;
            }

            dispatch({
                type: CREATE_USER_SUCCESS,
            });
            resetForm(dispatch, 'createUser');
            dispatch(push('/admin'));
        }));
    };
}
