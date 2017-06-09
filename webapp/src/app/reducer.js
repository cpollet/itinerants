import authReducer from './auth/reducer';
import resetPasswordReducer from './resetPassword/reducer';
import planningReducer from './planning/reducer';
import futureEventsReducer from './futureEvents/reducer';
import availabilityReducer from './availability/reducer';
import attendanceReducer from './attendance/reducer';
import userCreationReducer from './users/create/reducer';
import userModificationReducer from './users/edit/reducer';
import adminStateReducer from './admin/reducer';
import {SERVER_ERROR} from './actions';
import {SERVER_ERROR_RESET} from './actions';

const initialState = {
    fetchError: false
};

function appStateReducer(state = initialState, action) {
    switch (action.type) {
        case SERVER_ERROR:
            return Object.assign({}, state, {
                fetchError: true,
            });
        case SERVER_ERROR_RESET:
            return Object.assign({}, state, {
                fetchError: false,
            });
    }

    return state;
}

export default function reducer(state = {}, action) {
    return {
        admin: adminStateReducer(state.admin, action),
        appState: appStateReducer(state.appState, action),
        auth: authReducer(state.auth, action),
        futureEvents: futureEventsReducer(state.futureEvents, action),
        availabilities: availabilityReducer(state.availabilities, action),
        attendances: attendanceReducer(state.attendances, action),
        planning: planningReducer(state.planning, action),
        resetPassword: resetPasswordReducer(state.resetPassword, action),
        userCreation: userCreationReducer(state.userCreation, action),
        userModification: userModificationReducer(state.userModification, action),
    };
}
