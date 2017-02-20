import React from 'react';
import {Link} from 'react-router';

class App extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <h1>Itinérants</h1>
                <h2>Menu</h2>
                <ul>
                    <li><Link to={'/past'}>Past</Link></li>
                    <li><Link to={'/future'}>Future</Link></li>
                    <li><Link to={'/settings'}>Settings</Link></li>
                </ul>
                <h2>Content</h2>
                {this.props.children}
            </div>
        );
    }
}

App.propTypes = {
    children: React.PropTypes.node
};

export default App;


