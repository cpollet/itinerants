import {push} from 'react-router-redux';
import {
    FETCH_PLAN_PROPOSAL,
    RECEIVE_PLAN_PROPOSAL,
    SYNC_PLAN,
    SYNC_PLAN_SUCCESS,
    TOGGLE_PLANNING,
    TOGGLE_SELECTION
} from '../actions';
import {authenticatedFetch} from '../helpers';

export function togglePlanning(eventId) {
    return function (dispatch) {
        dispatch({
            type: TOGGLE_PLANNING,
            eventId: eventId
        });
    };
}

export function fetchPlanProposal(eventIds) {
    return function (dispatch, getState) {
        dispatch({
            type: FETCH_PLAN_PROPOSAL,
        });

        const params = eventIds.map(e => 'eventId=' + e).join('&');

        if (params === '') {
            dispatch(push('/future'));
        }

        return authenticatedFetch('/api/attendances?' + params, dispatch, getState())
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                }
                return null;
            })
            .then(json => {
                if (json !== null) {
                    dispatch({
                        type: RECEIVE_PLAN_PROPOSAL,
                        payload: json
                    });
                }
            });
    };
}

export function toggleSelection(eventId, personId) {
    return function (dispatch) {
        dispatch({
            type: TOGGLE_SELECTION,
            eventId: eventId,
            personId: personId
        });
    };
}

export function savePlan() {
    return function (dispatch, getState) {
        console.log('save plan');
        const payload = getState()['app'].planning.proposal.events.map(e => ({
            eventId: e.eventId,
            personIds: e.selectedPeople
        }));

        dispatch({
            type: SYNC_PLAN,
        });

        authenticatedFetch('/api/attendances', dispatch, getState(), {
            method: 'PUT',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.ok) {
                dispatch({
                    type: SYNC_PLAN_SUCCESS
                });
            } else {
                response.json().then((msg) => console.log(msg));
            }
        }).catch((ex) => console.log(ex));
    };
}
