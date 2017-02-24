import React from 'react';
import moment from 'moment';
import Checkbox from './Checkbox';
import {toggleAvailability} from '../reducers/actions';
import styles from './Event.css';
import {connect} from 'react-redux';


class Event extends React.Component {
    constructor() {
        super();
    }

    render() {
        //console.log('render Event', this.props);
        const date = moment(this.props.event.dateTime);

        return (
            <tr className={styles.component}>
                <td>
                    <Checkbox checked={this.props.available}
                              onClick={this.props.toggle.bind(this, this.props.event.eventId)}/>
                </td>
                <td><Checkbox checked={false}/></td>
                <td>{date.format('D MMMM, HH:mm')}</td>
                <td>{this.props.event.name}</td>
            </tr>
        );
    }
}

Event.propTypes = {
    event: React.PropTypes.object.isRequired,
    available: React.PropTypes.bool.isRequired,
    toggle: React.PropTypes.func.isRequired,
};

function mapDispatchToProps(dispatch) {
    return {
        toggle: (eventId) => dispatch(toggleAvailability(eventId))
    };
}

export default connect(() => ({}), mapDispatchToProps)(Event);
