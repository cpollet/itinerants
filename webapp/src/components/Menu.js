import React from 'react';
import {Link} from 'react-router';
import {renderIf} from '../lib/helpers';

class Menu extends React.Component {

    render() {
        return (
            <ul>
                {renderIf(!this.props.isAuthenticated, <li><Link to={'/login'}>Login</Link></li>)}
                {renderIf(this.props.isAuthenticated, <li><Link to={'/logout'}>Logout</Link></li>)}
                {renderIf(this.props.isAuthenticated, <li><Link to={'/past'}>Past</Link></li>)}
                {renderIf(this.props.isAuthenticated, <li><Link to={'/future'}>Future</Link></li>)}
                {renderIf(this.props.isAuthenticated, <li><Link to={'/settings'}>Settings</Link></li>)}
                {renderIf(this.props.isAdmin, <li><Link to={'/admin'}>Admin</Link></li>)}
            </ul>
        );
    }
}

Menu.propTypes = {
    isAdmin: React.PropTypes.bool.isRequired,
    isAuthenticated: React.PropTypes.bool.isRequired
};

export default Menu;
