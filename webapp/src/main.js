import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import FutureEventsContainer from './components/containers/FutureEventsContainer';
import NoMatch from './components/NoMatch';
import App from './components/App';
import LoginContainer from './components/containers/LoginContainer';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware, compose, combineReducers} from 'redux';
import {logger} from 'redux-logger';
import appReducer from './reducers/reducers';
import thunkMiddleware from 'redux-thunk';
import moment from 'moment';
import {sync, decreaseSyncTimeout} from './reducers/actions';
import SyncManager from './SyncManager';
import {reducer as formReducer} from './lib/form/FormContainer';
import FormProvider from './lib/form/FormProvider';

document.addEventListener('DOMContentLoaded', function () {
    moment.locale('fr');

    const reducer = combineReducers({
        app: appReducer,
        forms: formReducer
    });

    const store = createStore(reducer, compose(
        applyMiddleware(thunkMiddleware),
        applyMiddleware(logger),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    ));

    const syncManager = new SyncManager({
        syncTimerInterval: 1000,
        stale: () => store.getState().app.serverSync.stale,
        syncPending: () => store.getState().app.serverSync.syncPending,
        syncTimedOut: () => store.getState().app.serverSync.syncTimeoutMs === 0,
        decreaseSyncTimeout: () => store.dispatch(decreaseSyncTimeout()),
        sync: () => store.dispatch(sync()),
    });
    store.subscribe(syncManager.listenerFactory());

    ReactDOM.render((
        <Provider store={store}>
            <FormProvider stateKey="forms">
                <Router history={browserHistory}>
                    <Route path="/" component={App}>
                        <IndexRoute component={FutureEventsContainer}/>
                        <Route path="login" component={LoginContainer}/>
                        <Route path="future" component={FutureEventsContainer}/>
                        <Route path="past" component={NoMatch}/>
                        <Route path="settings" component={NoMatch}/>
                        <Route path="*" component={NoMatch}/>
                    </Route>
                </Router>
            </FormProvider>
        </Provider>
    ), document.getElementById('root'));
});
