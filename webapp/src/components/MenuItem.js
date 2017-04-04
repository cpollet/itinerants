import React from 'react';
import styles from './MenuItem.css';

class MenuItem extends React.Component {
    render() {
        const active = this.props.active ? styles.active : '';
        return (
            <li className={styles.component + ' ' + active}>
                {this.props.children}
            </li>
        );
    }
}

MenuItem.propTypes = {
    children: React.PropTypes.node,
    active: React.PropTypes.bool
};

export default MenuItem;
