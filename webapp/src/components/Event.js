import React from 'react';
import moment from 'moment';

class Event extends React.Component {
    constructor() {
        super();
    }

    render() {
        const date = moment(this.props.event.dateTime);
        return (
            <div>
                <input type="checkbox"/>
                <input type="checkbox" disabled="disabled"/>
                {this.props.event.name} {date.format('D MMMM YYYY, HH:mm')}
            </div>
        );
    }
}

Event.propTypes = {
    event: React.PropTypes.object.isRequired
};

export default Event;
