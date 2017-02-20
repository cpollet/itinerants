import React from 'react';
import ReactDOM from 'react-dom';
import FutureEvents from './components/FutureEvents';
import PastEvents from './components/PastEvents';
import NoMatch from './components/NoMatch';
import App from './components/App';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';

document.addEventListener('DOMContentLoaded', function () {
    ReactDOM.render(
        (
            <Router history={browserHistory}>
                <Route path="/" component={App}>
                    <IndexRoute component={FutureEvents}/>
                    <Route path="future" component={FutureEvents}/>
                    <Route path="past" component={PastEvents}/>
                    <Route path="settings" component={NoMatch}/>
                    <Route path="*" component={NoMatch}/>
                </Route>
            </Router>
        ),
        document.getElementById('root')
    );
});
