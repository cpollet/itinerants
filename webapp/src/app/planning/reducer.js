import {
    RECEIVE_PLAN_PROPOSAL,
    SYNC_PLAN,
    SYNC_PLAN_SUCCESS,
    TOGGLE_PLANNING,
    TOGGLE_SELECTION
} from '../actions';

const initialState = {
    eventsToPlan: [],
    proposal: {
        events: [],
        attendees: {}
    },
    sync: { // TODO rename to serverSync
        pending: false,
        syncFailure: false
    }
};

export default function (state = initialState, action) {
    switch (action.type) {
        case TOGGLE_PLANNING: {
            let eventsArray = state.eventsToPlan;

            if (eventsArray.indexOf(action.eventId) == -1) {
                eventsArray = [...eventsArray, action.eventId];
            } else {
                eventsArray = eventsArray.filter((v) => v !== action.eventId);
            }

            return Object.assign({}, state, {
                eventsToPlan: eventsArray
            });
        }
        case RECEIVE_PLAN_PROPOSAL:
            return Object.assign({}, state, {
                proposal: action.payload
            });
        case TOGGLE_SELECTION: {
            const otherEvents = state.proposal.events.filter(e => e.eventId !== action.eventId);
            const event = state.proposal.events.filter(e => e.eventId === action.eventId)[0];

            const newSelectedPeople = ((selectedPeople, person) => {
                if (selectedPeople.indexOf(person) === -1) {
                    return [...selectedPeople, person];
                } else {
                    return selectedPeople.filter(v => v !== person);
                }
            })(event.selectedPeople, action.personId);

            const newEvent = Object.assign({}, event, {
                selectedPeople: newSelectedPeople
            });

            const newEvents = [...otherEvents, newEvent];

            const newProposal = Object.assign({}, state.proposal, {
                events: newEvents
            });

            return Object.assign({}, state, {
                proposal: newProposal
            });
        }
        case SYNC_PLAN: {
            return Object.assign({}, state, {
                sync: {
                    pending: true,
                    syncFailure: false
                }
            });
        }
        case SYNC_PLAN_SUCCESS: {
            return Object.assign({}, state, {
                sync: {
                    pending: false,
                    syncFailure: false
                }
            });
        }
    }
    return state;
}