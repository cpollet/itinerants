import {
    PLANNING_PROPOSAL_FETCH,
    PLANNING_PROPOSAL_FETCH_SUCCESS,
    PLANNING_SAVE_START,
    PLANNING_SAVE_SUCCESS,
    PLANNING_TOGGLE_EVENT_SELECTION,
    PLANNING_TOGGLE_PERSON_SELECTION
} from '../actions';

const initialState = {
    eventsToPlan: [],
    proposal: {
        events: [],
        attendees: {}
    },
    sync: { // TODO rename to serverSync
        ready: false,
        pending: false,
        syncFailure: false
    }
};

export default function (state = initialState, action) {
    switch (action.type) {
        case PLANNING_TOGGLE_EVENT_SELECTION: {
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
        case PLANNING_PROPOSAL_FETCH:
            return Object.assign({}, state, {
                sync: {
                    pending: state.sync.pending,
                    syncFailure: state.sync.syncFailure,
                    ready: false
                }
            });
        case PLANNING_PROPOSAL_FETCH_SUCCESS:
            return Object.assign({}, state, {
                proposal: action.payload,
                sync: {
                    pending: state.sync.pending,
                    syncFailure: state.sync.syncFailure,
                    ready: true
                }
            });
        case PLANNING_TOGGLE_PERSON_SELECTION: {
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
        case PLANNING_SAVE_START: {
            return Object.assign({}, state, {
                sync: {
                    pending: true,
                    syncFailure: false,
                    ready: state.sync.ready
                }
            });
        }
        case PLANNING_SAVE_SUCCESS: {
            return Object.assign({}, state, {
                sync: {
                    pending: false,
                    syncFailure: false,
                    ready: state.sync.ready
                }
            });
        }
    }
    return state;
}