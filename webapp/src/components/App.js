import React from 'react';
import MenuContainer from './containers/MenuContainer';
import InfoStaleState from './containers/InfoStaleState';
import AlertSyncFail from './containers/AlertSyncFail';
import styles from './App.css';

class App extends React.Component {
    render() {
        return (
            <div className={styles.component}>

                <h1>Itinérants</h1>

                <div className={styles.container}>
                    <div className={styles.menuContainer}>
                        <MenuContainer location={this.props.location}/>
                    </div>
                    test
                </div>

                <h2>Menu</h2>


                <InfoStaleState text="Sauvegarde en cours..."/>
                <AlertSyncFail title="Erreur" text="impossible de sauvegarder"/>
                <h2>Content</h2>
                {this.props.children}
            </div>
        );
    }
}

App.propTypes = {
    children: React.PropTypes.node,
    location: React.PropTypes.object
};

export default App;


