import React from 'react';
import Checkbox from './core/Checkbox';
import styles from './Event.css';

class Event extends React.Component {
    render() {
        return (
            <tr className={styles.component}>
                <td>
                    <Checkbox checked={this.props.event.available}
                              onClick={this.props.toggle.bind(this, this.props.event.id)}/>
                </td>
                <td><Checkbox checked={false} disabled={true}/></td>
                <td>{this.props.event.dateTime.format('D MMMM YYYY')}</td>
                <td>{this.props.event.dateTime.format('HH:mm')}</td>
                <td>{this.props.event.name}</td>
            </tr>
        );
    }
}

Event.propTypes = {
    event: React.PropTypes.shape({
        id: React.PropTypes.string.isRequired,
        name: React.PropTypes.string.isRequired,
        dateTime: React.PropTypes.object.isRequired, // must be a moment() object
        available: React.PropTypes.bool.isRequired
    }).isRequired,
    toggle: React.PropTypes.func.isRequired,
};

export default Event;
