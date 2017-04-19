import React from 'react';
import {renderIf} from '../lib/helpers';
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
                    <div key={proposal.eventId} className={styles.container}>
                        <Panel
                            title={this.blockTitle(proposal)}>
                            <div className={styles.box}>
                                {proposal.availablePeople.map(p =>
                                    <div key={proposal.eventId + ':' + p.personId} className={styles.inner}>
                                        <Checkbox checked={this.isSelected(proposal.selectedPeople, p.personId)}
                                                  label={p.name + ' (' + p.attendancesCount + ')'}
                                                  onClick={this.props.toggleSelection.bind(this, proposal.eventId, p.personId)}/>
                                    </div>
                                )}
                            </div>
                            <div className={styles.stats}>
                                {renderIf(
                                    this.correctCount(proposal),
                                    this.stats(proposal),
                                    <span style={{color: 'red'}}>{this.stats(proposal)}</span>
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

    blockTitle(proposal) {
        return proposal.eventName + ', ' + proposal.dateTime.format('ddd D MMMM YYYY [à] HH:mm');
    }

    stats(proposal) {
        return proposal.selectedPeople.length + '/' + proposal.eventSize;
    }

    correctCount(proposal) {
        return proposal.selectedPeople.length === proposal.eventSize;
    }

    isSelected(selectedPeople, personId) {
        return selectedPeople.indexOf(personId) > -1;
    }
}

Planning.propTypes = {
    proposal: React.PropTypes.array.isRequired,
    back: React.PropTypes.func.isRequired,
    toggleSelection: React.PropTypes.func.isRequired,
};

export default Planning;
