'use strict';

import React from 'react';
import {render} from 'react-dom';

import { Router, Route, IndexRoute } from 'react-router'

import App from './components/App.jsx';

require('../semantic/dist/semantic.css');
require('imports?jQuery=jquery!../semantic/dist/semantic.js');

render((
    <Router>
        <Route path="/" component={App}/>
    </Router>
), document.getElementById('container'));