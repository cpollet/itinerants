import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import FutureEventsContainer from './app/futureEvents/containers/FutureEventsContainer';
import PlanContainer from './app/planning/containers/PlanContainer';
import NoMatch from './app/error404/screens/NoMatch';
import App from './app/App';
import LoginContainer from './app/auth/containers/LoginContainer';
import LogoutContainer from './app/auth/containers/LogoutContainer';
import ResetPasswordContainer from './app/resetPassword/containers/ResetPasswordContainer';
import {browserHistory, IndexRoute, Route, Router} from 'react-router';
import {Provider} from 'react-redux';
import {applyMiddleware, combineReducers, compose, createStore} from 'redux';
import {logger} from 'redux-logger';
import appReducer from './app/reducer';
import thunk from 'redux-thunk';
import moment from 'moment';
import {decreaseSyncTimeout, sync} from './app/availability/actions';
import {LOGIN_SUCCESS, LOGOUT} from './app/actions';
import SyncManager from './app/availability/SyncManager';
import {reducer as formReducer} from './lib/form/FormContainer';
import FormProvider from './lib/form/FormProvider';
import {routerActions, routerMiddleware, routerReducer, syncHistoryWithStore} from 'react-router-redux';
import {UserAuthWrapper} from 'redux-auth-wrapper';
import CreateUserContainer from './app/users/create/containers/CreateUserContainer';
import './main.less';
import AdminMenu from './app/admin/screens/AdminMenu';
import EditUserContainer from './app/users/edit/containers/EditUserContainer';

document.addEventListener('DOMContentLoaded', function () {
    moment.locale('fr');

    const reducer = combineReducers({
        routing: routerReducer,
        app: appReducer,
        forms: formReducer,
        rememberMe: (state = {}) => state
    });

    const rootReducer = (state, action) => {
        switch (action.type) {
            case LOGIN_SUCCESS: {
                state = Object.assign({}, state, {
                    rememberMe: {
                        username: action.username
                    }
                });
                sessionStorage.setItem('auth', JSON.stringify(action));
                break;
            }
            case LOGOUT: {
                // http://stackoverflow.com/questions/35622588/how-to-reset-the-state-of-a-redux-store
                const {routing, rememberMe} = state;
                state = {
                    rememberMe,
                    routing
                };
                sessionStorage.removeItem('auth');
                break;
            }
        }

        return reducer(state, action);
    };

    const routingMiddleware = routerMiddleware(browserHistory);

    const store = createStore(rootReducer, compose(
        applyMiddleware(thunk),
        applyMiddleware(logger),
        applyMiddleware(routingMiddleware),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    ));
    const history = syncHistoryWithStore(browserHistory, store);

    const syncManager = new SyncManager({
        syncTimerInterval: 1000,
        stale: () => store.getState().app.availabilities.serverSync.stale,
        syncPending: () => store.getState().app.availabilities.serverSync.syncPending,
        syncTimedOut: () => store.getState().app.availabilities.serverSync.syncTimeoutMs === 0,
        decreaseSyncTimeout: () => store.dispatch(decreaseSyncTimeout()),
        sync: () => store.dispatch(sync()),
    });
    store.subscribe(syncManager.listenerFactory());

    if (sessionStorage.getItem('auth')) {
        store.dispatch(JSON.parse(sessionStorage.getItem('auth')));
    }

    function authData(state) {
        if (state.app.auth.token !== null) {
            return {
                token: state.app.auth.token,
                isAdmin: state.app.auth.roles.indexOf('admin') > -1,
            };
        } else {
            return {
                token: null,
                isAdmin: false,
            };
        }
    }

    const UserIsAuthenticated = UserAuthWrapper({
        authSelector: state => authData(state),
        predicate: user => user.token !== null,
        redirectAction: routerActions.replace
    });
    const Authenticated = UserIsAuthenticated((props) => React.cloneElement(props.children, props));

    const UserIsAdmin = UserAuthWrapper({
        authSelector: state => authData(state),
        predicate: user => user.isAdmin,
        redirectAction: routerActions.replace,
        failureRedirectPath: () => '/future',
        allowRedirectBack: false
    });
    // const Admin = UserIsAdmin((props) => React.cloneElement(props.children, props));

    const UserIsNotAuthenticated = UserAuthWrapper({
        authSelector: state => authData(state),
        redirectAction: routerActions.replace,
        wrapperDisplayName: 'UserIsNotAuthenticated',
        predicate: user => user.token === null,
        failureRedirectPath: (state, ownProps) => ownProps.location.query.redirect || '/',
        allowRedirectBack: false
    });

    ReactDOM.render((
        <Provider store={store}>
            <FormProvider stateKey="forms">
                <Router history={history}>
                    <Route path="/" component={App}>
                        <Route path="login" component={UserIsNotAuthenticated(LoginContainer)}/>
                        <Route path="logout" component={LogoutContainer}/>
                        <Route path="users/passwords/:token"
                               component={UserIsNotAuthenticated(ResetPasswordContainer)}/>
                        <Route component={Authenticated}>
                            <IndexRoute component={FutureEventsContainer}/>
                            <Route path="future" component={FutureEventsContainer}/>
                            <Route path="profile" component={EditUserContainer}/>
                        </Route>
                        <Route path="admin" component={UserIsAdmin(AdminMenu)}>
                            <Route path="plan" component={PlanContainer}/>
                            <Route path="users/create" component={CreateUserContainer}/>
                        </Route>
                        <Route path="*" component={NoMatch}/>
                    </Route>
                </Router>
            </FormProvider>
        </Provider>
    ), document.getElementById('root'));
});
