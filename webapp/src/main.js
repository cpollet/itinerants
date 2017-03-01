import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import FutureEventsContainer from './components/containers/FutureEventsContainer';
import NoMatch from './components/NoMatch';
import App from './components/App';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware, compose} from 'redux';
import reducer from './reducers/reducers';
import thunkMiddleware from 'redux-thunk';
import moment from 'moment';
import {sync, decreaseSyncTimeout} from './reducers/actions';
import SyncManager from './SyncManager';

document.addEventListener('DOMContentLoaded', function () {
    moment.locale('fr');

    const store = createStore(reducer, compose(
        applyMiddleware(thunkMiddleware),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    ));

    const syncManager = new SyncManager({
        syncTimerInterval: 1000,
        stale: () => store.getState().serverSync.stale,
        syncPending: () => store.getState().serverSync.syncPending,
        syncTimedOut: () => store.getState().serverSync.syncTimeoutMs === 0,
        onTick: () => store.dispatch(decreaseSyncTimeout()),
        sync: () => store.dispatch(sync()),
    });
    store.subscribe(syncManager.listenerFactory());

    ReactDOM.render((
        <Provider store={store}>
            <Router history={browserHistory}>
                <Route path="/" component={App}>
                    <IndexRoute component={FutureEventsContainer}/>
                    <Route path="future" component={FutureEventsContainer}/>
                    <Route path="past" component={NoMatch}/>
                    <Route path="settings" component={NoMatch}/>
                    <Route path="*" component={NoMatch}/>
                </Route>
            </Router>
        </Provider>
    ), document.getElementById('root'));
});
