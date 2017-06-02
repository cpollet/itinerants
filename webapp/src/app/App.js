import React from 'react';
import MenuContainer from './menu/containers/MenuContainer';
import InfoStaleState from './availability/containers/InfoStaleState';
import AlertSyncFail from './availability/containers/AlertSyncFail';
import styles from './App.less';
import Alert from '../widgets/Alert';

class App extends React.Component {
    render() {
        return (
            <div className={styles.component}>

                <h1>Itinérants</h1>

                {this.props.fetchError && <Alert type="error">Une erreur est survenue, réessaie plus tard</Alert>}

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
    location: React.PropTypes.object,
    fetchError: React.PropTypes.bool.isRequired,
};

App.defaultProps = {
    fetchError: false,
};

export default App;


