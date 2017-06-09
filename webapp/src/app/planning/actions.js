import {push} from 'react-router-redux';
import {
    PLANNING_PROPOSAL_FETCH,
    PLANNING_PROPOSAL_FETCH_SUCCESS,
    PLANNING_SAVE_START,
    PLANNING_SAVE_SUCCESS,
    PLANNING_TOGGLE_EVENT_SELECTION,
    PLANNING_TOGGLE_PERSON_SELECTION
} from '../actions';
import {authenticatedFetch, guardedFetch} from '../helpers';

export function togglePlanning(eventId) {
    return function (dispatch) {
        dispatch({
            type: PLANNING_TOGGLE_EVENT_SELECTION,
            eventId: eventId
        });
    };
}

export function fetchPlanProposal(eventIds) {
    return function (dispatch, getState) {
        dispatch({
            type: PLANNING_PROPOSAL_FETCH,
        });

        const params = eventIds.map(e => 'eventId=' + e).join('&');

        if (params === '') {
            dispatch(push('/future'));
        }

        return guardedFetch(dispatch, authenticatedFetch('/api/attendances?' + params, dispatch, getState())
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                }
                return null;
            })
            .then(json => {
                if (json !== null) {
                    dispatch({
                        type: PLANNING_PROPOSAL_FETCH_SUCCESS,
                        payload: json
                    });
                }
            }));
    };
}

export function toggleSelection(eventId, personId) {
    return function (dispatch) {
        dispatch({
            type: PLANNING_TOGGLE_PERSON_SELECTION,
            eventId: eventId,
            personId: personId
        });
    };
}

export function savePlan() {
    return function (dispatch, getState) {
        const payload = getState()['app'].planning.proposal.events.map(e => ({
            eventId: e.eventId,
            personIds: e.selectedPeople
        }));

        dispatch({
            type: PLANNING_SAVE_START,
        });

        guardedFetch(dispatch, authenticatedFetch('/api/attendances', dispatch, getState(), {
            method: 'PUT',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.ok) {
                dispatch({
                    type: PLANNING_SAVE_SUCCESS
                });
            } else {
                response.json().then((msg) => {
                    throw msg;
                });
            }

            fetchPlanProposal(getState()['app'].planning.proposal.events.map(e => e.eventId))(dispatch, getState);
        }));
    };
}
