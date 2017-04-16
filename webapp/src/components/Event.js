import React from 'react';
import Checkbox from './core/Checkbox';
import styles from './Event.css';
import {renderIf} from '../lib/helpers';

class Event extends React.Component {
    render() {
        return (
            <tr className={styles.component}>
                <td>
                    <Checkbox checked={this.props.event.available}
                              onClick={this.props.toggleAvailability.bind(this, this.props.event.id)}/>
                </td>
                <td><Checkbox checked={false} disabled={true}/></td>
                <td>{this.props.event.dateTime.format('HH:mm[, le] ddd D MMMM YYYY')}</td>
                <td>{this.props.event.name}</td>
                {renderIf(this.props.isAdmin,
                    <td>
                        <Checkbox checked={this.props.event.toPlan}
                                  onClick={this.props.togglePlanning.bind(this, this.props.event.id)}/>
                    </td>
                )}
            </tr>
        );
    }
}

Event.propTypes = {
    event: React.PropTypes.shape({
        id: React.PropTypes.string.isRequired,
        name: React.PropTypes.string.isRequired,
        dateTime: React.PropTypes.object.isRequired, // must be a moment() object
        available: React.PropTypes.bool.isRequired,
        toPlan: React.PropTypes.bool.isRequired,
    }).isRequired,
    toggleAvailability: React.PropTypes.func.isRequired,
    togglePlanning: React.PropTypes.func.isRequired,
    isAdmin: React.PropTypes.bool.isRequired,
};

export default Event;
