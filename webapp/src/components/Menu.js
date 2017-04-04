import React from 'react';
import {Link} from 'react-router';
import {renderIf} from '../lib/helpers';
import MenuItem from './MenuItem';
import styles from './Menu.css';

class Menu extends React.Component {

    render() {
        return (
            <ul className={styles.component}>
                {renderIf(
                    !this.props.isAuthenticated,
                    <MenuItem active={this.props.active === 'login'}>
                        <Link to={'/login'}>Login</Link>
                    </MenuItem>
                )}
                {renderIf(
                    this.props.isAuthenticated,
                    <MenuItem active={this.props.active === 'future'}>
                        <Link to={'/future'}>Future</Link>
                    </MenuItem>
                )}
                {renderIf(
                    this.props.isAuthenticated,
                    <MenuItem active={this.props.active === 'past'}>
                        <Link to={'/past'}>Past</Link>
                    </MenuItem>
                    )}
                {renderIf(
                    this.props.isAuthenticated,
                    <MenuItem active={this.props.active === 'settings'}>
                        <Link to={'/settings'}>Settings</Link>
                    </MenuItem>
                )}
                {renderIf(
                    this.props.isAdmin,
                    <MenuItem active={this.props.active === 'admin'}>
                        <Link to={'/admin'}>Admin</Link>
                    </MenuItem>
                )}

                {renderIf(
                    this.props.isAuthenticated,
                    <MenuItem>
                        <Link to={'/logout'}>Logout</Link>
                    </MenuItem>
                )}
            </ul>
        );
    }
}

Menu.propTypes = {
    isAdmin: React.PropTypes.bool.isRequired,
    isAuthenticated: React.PropTypes.bool.isRequired,
    active: React.PropTypes.string
};

export default Menu;
