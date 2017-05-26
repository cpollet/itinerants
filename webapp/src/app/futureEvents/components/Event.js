import React from 'react';
import Checkbox from '../../../widgets/Checkbox';
import styles from './Event.less';
import {renderIf} from '../../../lib/helpers';

class Event extends React.Component {
    toggle() {
        if (!this.props.event.attending) {
            this.props.toggleAvailability(this.props.event.id);
        }
    }

    render() {
        return (
            <div className={styles.component + (this.props.event.attending ? ' ' + styles.attending : '')}>
                <div onClick={this.toggle.bind(this)}>
                    <div className={styles.checkboxContainer}>
                        <Checkbox checked={this.props.event.available} disabled={this.props.event.attending}/>
                    </div>
                    <div className={styles.eventDetailContainer}>
                        <div>{this.props.event.dateTime.format('ddd D MMMM YYYY, HH:mm')}</div>
                        <div>{this.props.event.name}</div>
                    </div>
                </div>
                {renderIf(this.props.isAdmin,
                    <div className={styles.checkboxContainer}>
                        <Checkbox checked={this.props.event.toPlan}
                                  onClick={this.props.togglePlanning.bind(this, this.props.event.id)}/>
                    </div>
                )}
            </div>
        );
    }
}

Event.propTypes = {
    event: React.PropTypes.shape({
        id: React.PropTypes.string.isRequired,
        name: React.PropTypes.string.isRequired,
        dateTime: React.PropTypes.object.isRequired, // must be a moment() object
        available: React.PropTypes.bool.isRequired,
        attending: React.PropTypes.bool.isRequired,
        toPlan: React.PropTypes.bool.isRequired,
    }).isRequired,
    toggleAvailability: React.PropTypes.func.isRequired,
    togglePlanning: React.PropTypes.func.isRequired,
    isAdmin: React.PropTypes.bool.isRequired,
};

export default Event;
