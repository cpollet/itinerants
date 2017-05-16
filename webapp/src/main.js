import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import FutureEventsContainer from './components/containers/FutureEventsContainer';
import PlanContainer from './components/containers/PlanContainer';
import NoMatch from './screens/NoMatch';
import App from './components/App';
import LoginContainer from './components/containers/LoginContainer';
import LogoutContainer from './components/containers/LogoutContainer';
import ResetPasswordContainer from './components/containers/ResetPasswordContainer';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware, compose, combineReducers} from 'redux';
import {logger} from 'redux-logger';
import appReducer from './reducers/reducers';
import thunk from 'redux-thunk';
import moment from 'moment';
import {sync, decreaseSyncTimeout} from './reducers/actions';
import {LOGIN_SUCCESS, LOGOUT} from './reducers/actions';
import SyncManager from './SyncManager';
import {reducer as formReducer} from './lib/form/FormContainer';
import FormProvider from './lib/form/FormProvider';
import {routerReducer, routerMiddleware, routerActions, syncHistoryWithStore} from 'react-router-redux';
import {UserAuthWrapper} from 'redux-auth-wrapper';
import './main.less';

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
        stale: () => store.getState().app.serverSync.stale,
        syncPending: () => store.getState().app.serverSync.syncPending,
        syncTimedOut: () => store.getState().app.serverSync.syncTimeoutMs === 0,
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
    const Admin = UserIsAdmin((props) => React.cloneElement(props.children, props));

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
                        <Route path="resetPassword" component={UserIsNotAuthenticated(ResetPasswordContainer)}/>
                        <Route component={Authenticated}>
                            <IndexRoute component={FutureEventsContainer}/>
                            <Route path="future" component={FutureEventsContainer}/>
                            <Route path="past" component={NoMatch}/>
                            <Route path="settings" component={NoMatch}/>
                        </Route>
                        <Route component={Admin}>
                            <Route path="plan" component={PlanContainer}/>
                        </Route>
                        <Route path="*" component={NoMatch}/>
                    </Route>
                </Router>
            </FormProvider>
        </Provider>
    ), document.getElementById('root'));
});
