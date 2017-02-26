import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import FutureEvents from './containers/FutureEvents';
import NoMatch from './components/NoMatch';
import App from './components/App';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware, compose} from 'redux';
import reducer from './reducers/reducers';
import thunkMiddleware from 'redux-thunk';
import moment from 'moment';

document.addEventListener('DOMContentLoaded', function () {
    moment.locale('fr');

    const store = createStore(reducer, compose(
        applyMiddleware(thunkMiddleware),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    ));

    ReactDOM.render((
        <Provider store={store}>
            <Router history={browserHistory}>
                <Route path="/" component={App}>
                    <IndexRoute component={FutureEvents}/>
                    <Route path="future" component={FutureEvents}/>
                    <Route path="past" component={NoMatch}/>
                    <Route path="settings" component={NoMatch}/>
                    <Route path="*" component={NoMatch}/>
                </Route>
            </Router>
        </Provider>
    ), document.getElementById('root'));
});
