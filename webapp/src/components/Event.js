import React from 'react';
import Checkbox from './Checkbox';
import {toggleAvailability} from '../reducers/actions';
import styles from './Event.css';
import {connect} from 'react-redux';

class Event extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <tr className={styles.component}>
                <td>
                    <Checkbox checked={this.props.event.available}
                              onClick={this.props.toggle.bind(this, this.props.event.id)}/>
                </td>
                <td><Checkbox checked={this.props.event.id===4} disabled={true}/></td>
                <td>{this.props.event.dateTime.format('D MMMM, HH:mm')}</td>
                <td>{this.props.event.name}</td>
            </tr>
        );
    }
}

Event.propTypes = {
    event: React.PropTypes.shape({
        id: React.PropTypes.number.isRequired,
        name: React.PropTypes.string.isRequired,
        dateTime: React.PropTypes.object.isRequired, // must be a moment() object
        available: React.PropTypes.bool.isRequired
    }).isRequired,
    toggle: React.PropTypes.func.isRequired,
};

function mapDispatchToProps(dispatch) {
    return {
        toggle: (eventId) => dispatch(toggleAvailability(eventId))
    };
}

export default connect(() => ({}), mapDispatchToProps)(Event);
