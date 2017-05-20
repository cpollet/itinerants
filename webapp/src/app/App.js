import React from 'react';
import MenuContainer from './menu/containers/MenuContainer';
import InfoStaleState from './availability/containers/InfoStaleState';
import AlertSyncFail from './availability/containers/AlertSyncFail';
import styles from './App.less';

class App extends React.Component {
    render() {
        return (
            <div className={styles.component}>

                <h1>Itinérants</h1>

                <div className={styles.container}>
                    <div className={styles.menuContainer}>
                        <MenuContainer location={this.props.location}/>
                    </div>
                    <div className={styles.contentContainer}>
                        <InfoStaleState text="Sauvegarde en cours..."/>
                        <AlertSyncFail title="Erreur" text="impossible de sauvegarder"/>
                        {this.props.children}
                    </div>
                </div>
            </div>
        );
    }
}

App.propTypes = {
    children: React.PropTypes.node,
    location: React.PropTypes.object
};

export default App;


