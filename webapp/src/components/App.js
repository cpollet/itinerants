import React from 'react';
import {Link} from 'react-router';

import InfoStaleState from './containers/InfoStaleState';
import AlertSyncFail from './containers/AlertSyncFail';

import styles from './App.css';

class App extends React.Component {
    render() {
        return (
            <div className={styles.component}>

                <h1>Itinérants</h1>
                <h2>Menu</h2>
                <ul>
                    <li><Link to={'/login'}>Login</Link></li>
                    <li><Link to={'/past'}>Past</Link></li>
                    <li><Link to={'/future'}>Future</Link></li>
                    <li><Link to={'/settings'}>Settings</Link></li>
                </ul>
                <InfoStaleState text="Sauvegarde en cours..."/>
                <AlertSyncFail title="Erreur" text="impossible de sauvegarder"/>
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


