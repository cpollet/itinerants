import React from 'react';
import {renderIf} from '../lib/helpers';
import Panel from '../components/core/Panel';
import Button from '../components/core/Button';
import PlanningRow from '../components/PlanningRow';
import styles from './Planning.less';

class Planning extends React.Component {
    render() {
        return (
            <div>
                <h2>Planning</h2>
                <div className={styles.panelsWrapper}>
                    {this.props.proposal.map(proposal =>
                        <div key={proposal.eventId} className={styles.panelWrapper}>
                            <Panel title={this.blockTitle(proposal)}>
                                <div className={styles.panelHeight}>
                                    <div className={styles.inner}>
                                        {proposal.availablePeople.map(person =>
                                            <PlanningRow
                                                key={proposal.eventId + ':' + person.personId}
                                                person={person}
                                                isSelected={this.isSelected(proposal.selectedPeople, person.personId)}
                                                onClick={this.props.toggleSelection.bind(this, proposal.eventId, person.personId)}/>
                                        )}
                                    </div>
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
                </div>
                <div className={styles.right}>
                    <Button type="default" onClick={this.props.back}>Retour</Button>
                    <Button type="primary" onClick={this.props.save}>Sauver</Button>
                </div>
            </div>
        );
    }

    blockTitle(proposal) {
        return (
            <div>
                <div className={styles.eventTitle}>
                    {proposal.eventName}
                </div>
                <div>
                    {proposal.dateTime.format('ddd D MMMM YYYY [à] HH:mm')}
                </div>
            </div>

        );
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
    save: React.PropTypes.func.isRequired,
    toggleSelection: React.PropTypes.func.isRequired,
};

export default Planning;
