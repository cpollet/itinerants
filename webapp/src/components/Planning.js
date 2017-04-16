import React from 'react';
import {Link} from 'react-router';
import Panel from './core/Panel';
import Checkbox from './core/Checkbox';
import Button from './core/Button';
import styles from './Planning.css';

class Planning extends React.Component {
    render() {
        return (
            <div>
                <h2>Planning</h2>
                {this.props.proposal.map(proposal =>
                    <div className={styles.container}>
                        <Panel key={proposal.eventId}
                               title={proposal.eventName + ', ' + proposal.dateTime.format('ddd D MMMM YYYY [à] HH:mm')}>
                            <div className={styles.box}>
                                {proposal.availablePeople.map(p =>
                                    <div key={p.personId} className={styles.inner}>
                                        <Checkbox checked={this.isSelected(proposal.selectedPeople, p.personId)}
                                                  label={p.name}/>
                                    </div>
                                )}
                            </div>
                        </Panel>
                    </div>
                )}
                <div className={styles.right}>
                    <Button type="default" onClick={this.props.back}>Retour</Button>
                    <Button type="primary">Sauver</Button>
                </div>
            </div>
        );
    }

    isSelected(selectedPeople, personId) {
        return selectedPeople.indexOf(personId) > -1;
    }
}

Planning.propTypes = {
    proposal: React.PropTypes.array.isRequired,
    back: React.PropTypes.func.isRequired,
};

export default Planning;
